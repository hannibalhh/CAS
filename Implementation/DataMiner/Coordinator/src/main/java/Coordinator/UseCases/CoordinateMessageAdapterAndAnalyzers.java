package Coordinator.UseCases;

import Coordinator.Interface.IMessageChunkCompletedNotification;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import DataTypes.DataTypeInterfaces.IInformation;
import DataTypes.DataTypeInterfaces.ILocation;
import DataTypes.Information;
import DataTypes.InformationMetadata;
import DataTypes.Location;
import Interfaces.IAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import com.sun.deploy.util.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Raphael
 * Date: 20.10.13
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public class CoordinateMessageAdapterAndAnalyzers implements Runnable, IMessageChunkCompletedNotification {
    private IMessageAdapter messageAdapter;
    private List<IAnalyzer> analyzers;
    private List<IAnalyzer> analyzersWithResults;
    private List<Message> messageChunk;
    private boolean running = false;
    private List<Map<String, List<IInfo>>> analyzerResults;
    private Semaphore sleep = new Semaphore(0);
    private IRTCAdapter rtcAdapter;
    private Logger logger = LogManager.getLogger(CoordinateMessageAdapterAndAnalyzers.class);

    public CoordinateMessageAdapterAndAnalyzers(IMessageAdapter messageAdapter, IRTCAdapter rtcAdapter) { //Analyzer einzeln übergeben
        this.messageAdapter = messageAdapter;
        this.rtcAdapter = rtcAdapter;
        analyzers = new ArrayList<>();
        analyzerResults = new ArrayList<>();
        analyzersWithResults = new ArrayList<>();
        initializeCoordinator();
    }

    public void stopCoordinating() {
        this.running = false;
    }

    public void initializeCoordinator() {
        this.messageAdapter.addNewMessageChunkArrivedListener(this::onNewMessageChunkArrived);
    }

    private synchronized void onNewMessageChunkArrived(Iterable<Message> messageChunk) {
        try {
            this.handleNewWorkset(messageChunk);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        logger.info("A new chunk of messages has arrived. The Coordinator will now wait for the Analyzers.");
    }

    public synchronized void handleNewWorkset(Iterable<Message> workset) {
        this.messageChunk = new ArrayList<>();
        Iterator<Message> itt = workset.iterator();

        while (itt.hasNext()) {
            messageChunk.add(itt.next());
        }
    }

    @Override
    public void run() {
        this.running = true;

        while (running) {

            try {
                sleep.acquire();     //wait for results

                List<IInformation> aggregatedInfos = new ArrayList<>();
                for (Message currentMessageFromMessageChunk : messageChunk) {
                    List<IInfo> infoList = new ArrayList<>();
                    ILocation location = null;
                    for (Map<String, List<IInfo>> currentAnalyzerResult : analyzerResults) {
                        List<IInfo> currentIInfos = currentAnalyzerResult.get(currentMessageFromMessageChunk.getId());

                        if (currentIInfos == null) {
                            throw new RuntimeException("There are no registered analyzer results for the message with the id: " + currentMessageFromMessageChunk.getId());
                        }

                        currentIInfos.stream().forEach(x -> {
                            if (x != null) {
                                infoList.add(x);
                            }
                        });
                    }

                    List<String> pictureUrls = currentMessageFromMessageChunk.getPictureUrl();

                    if (pictureUrls == null) {
                        pictureUrls = new ArrayList<>();
                    }

/*                    List<String> strings = new ArrayList<>();
                    for (int i = 0; i < pictureUrls.size(); i++) {
                        strings.add(new String((byte[])pictureUrls.get(i)));
                    }*/

                    InformationMetadata metadata = new InformationMetadata(currentMessageFromMessageChunk.getHashtags(), pictureUrls);
                    IInformation information = new Information(currentMessageFromMessageChunk.getId(), currentMessageFromMessageChunk.getMessage(), currentMessageFromMessageChunk.getAuthor(), infoList, currentMessageFromMessageChunk.getPostTime(), metadata, currentMessageFromMessageChunk.getProvenance());

                    aggregatedInfos.add(information);
                }

                //send to RTCAdapter
                aggregatedInfos
                        .stream()
                        .forEach(
                                x -> {
                                    try {
                                        InformationMessage messageToSend = x.getMessage();

                                        rtcAdapter.send(messageToSend);
                                    } catch (TechnicalProblemException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    } catch (ConfigurationException e) {
                                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                    }
                                }
                        );

                this.analyzerResults.clear();

                logger.info("The message chunk has been processed successfully. A new message chunk will now be requested.");

                //notify Messageadapter
                messageAdapter.obtainNewMessages();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    @Deprecated
    public void sendMessageToRTCwithLocationMeta(InformationMessage messageToSend) {

        throw new RuntimeException("Method is sendMessageToRTCwithLocationMeta is deprecated. Pls use the new message format.");
        //check removed because metadata does not contains locations anymore
         /*
        if (messageToSend.getMetaData().getLocation() != null) {
            rtcAdapter.send(messageToSend);
            System.out.println("Message sent successfully!");
        } else {
            rtcAdapter.send(messageToSend);
            System.out.println("Failed to send message!");
        }
         */
    }

    public synchronized void registerAnalyzerResultForCurrentChunk(IAnalyzer analyzer, Map<String, List<IInfo>> resultsForChunk) {
        if (resultsForChunk == null) {
            throw new IllegalArgumentException("The result of an analyzer must not be null! If a analyzer doesn't find anything it must return an empty list.");
        }

        logger.debug("Analyzer " + analyzer + " has finished analyzing the message chunk.");

        Set<String> messageIdsFromResult = resultsForChunk.keySet();
        Set<String> messageIdsFromChunk = this.messageChunk.parallelStream().map(x -> x.getId()).collect(Collectors.toSet());
        messageIdsFromChunk.removeAll(messageIdsFromResult);

        if (messageIdsFromChunk.size() > 0) {
            throw new RuntimeException("The analyzer " + analyzer + " did not return a result for each message of this chunk. Results for the messages with the following ids were missing: " + StringUtils.join(messageIdsFromChunk, " "));
        }

        analyzerResults.add(resultsForChunk);
        analyzersWithResults.add(analyzer);
        if (analyzers.stream().allMatch(x -> analyzersWithResults.contains(x))) {
            analyzersWithResults.clear();
            logger.debug("All analyzers have returned their results.");
            sleep.release();
        }
    }

    public synchronized void registerAnalyzer(IAnalyzer analyzer) {
        analyzers.add(analyzer);
    }

}
