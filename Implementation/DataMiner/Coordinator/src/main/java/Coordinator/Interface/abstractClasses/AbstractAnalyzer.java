package Coordinator.Interface.abstractClasses;

import Coordinator.Interface.ICoordinator;
import DSPersistenceManager.Model.Message;
import DataTypes.DataTypeInterfaces.IInfo;
import Interfaces.IAnalyzer;
import MessageAdapter.Interface.IMessageAdapter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 31.10.13
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractAnalyzer implements IAnalyzer {
    private AnalyzerThread analyzerThread = null;
    private IMessageAdapter messageAdapter = null;
    private ICoordinator coordinator;
    private IAnalyzer currentAnalyzer;
    private Logger logger;
    private String analyzerName = null;

    protected AbstractAnalyzer(IMessageAdapter messageAdapter, ICoordinator coordinator) {
        this.messageAdapter = messageAdapter;
        this.coordinator = coordinator;
        messageAdapter.addNewMessageChunkArrivedListener(this::onNewMessageChunkArrived);
        coordinator.registerAnalyzer(this);
        this.currentAnalyzer = this;
        analyzerThread = new AnalyzerThread();
        logger = LogManager.getLogger(this.getClass());
    }

    public void onNewMessageChunkArrived(Iterable<Message> messageChunk) {
        this.analyzerThread.processWorkSet(messageChunk);
    }

    protected abstract Map<Message, List<IInfo>> processMessages(Iterable<Message> messages) throws Throwable;

    public void stopAnalyzer() {
        analyzerThread.stop();
    }

    public void startAnalyzer() {
        new Thread(analyzerThread).start();
    }

    private String getAnalyzerNameForLogging() {
        if (this.analyzerName == null) {
            this.analyzerName = this.getClass().getSimpleName();
        }

        return analyzerName;
    }

    private class AnalyzerThread implements Runnable {
        private boolean shouldRun = false;
        private Semaphore waitHandleForMessageChunk = new Semaphore(0);
        private List<Message> currentWorkSet;

        @Override
        public void run() {
            try {
                shouldRun = true;

                while (shouldRun) {
                    logger.debug("Analyzer " + getAnalyzerNameForLogging() + " is waiting for a message chunk to arrive.");
                    waitHandleForMessageChunk.acquire();
                    logger.debug("Analyzer " + getAnalyzerNameForLogging() + " got a message chunk and will now start to analyze the containing messages.");

                    if (!shouldRun) {
                        return;
                    }

                    Map<String, List<IInfo>> results = new HashMap<>();

                    try {
                        Map<Message, List<IInfo>> processedMessages = processMessages(currentWorkSet);

                        Iterator<Map.Entry<Message, List<IInfo>>> processedMessagesIterator = processedMessages.entrySet().iterator();

                        while(processedMessagesIterator.hasNext()){
                            Map.Entry<Message, List<IInfo>> nextEntry = processedMessagesIterator.next();

                            Message message = nextEntry.getKey();
                            List<IInfo> infos = nextEntry.getValue();

                            results.put(message.getId(), infos);
                        }
                    }
                    catch (Throwable e)
                    {
                        results = new HashMap<>();

                        for(Message message : currentWorkSet) {
                            results.put(message.getId(), new ArrayList<IInfo>());
                        }

                        logger.error("Error while processing message chunk in Analyzer: '"
                                + getAnalyzerNameForLogging()
                                + "'. The analyzer will continue running and return an empty result list for this chunk. The error message was:\n\n"
                                + ExceptionUtils.getStackTrace(e));
                    }

                    coordinator.registerAnalyzerResultForCurrentChunk(currentAnalyzer, results);
                    logger.debug("Analyzer " + getAnalyzerNameForLogging() + " has completed processing the message chunk and his passed its results to the Coordinator.");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                this.shouldRun = false;
            }
        }

        public void stop() {
            this.shouldRun = false;
        }

        public void processWorkSet(Iterable<Message> workSet) {
            this.currentWorkSet = new ArrayList<>();
            for(Message m : workSet) {
                currentWorkSet.add(m);
            }

            if (this.waitHandleForMessageChunk.availablePermits() > 0) {
                /**
                 * Todo: Überlegen
                 * Available permits kann > 0 sein, wenn die anderen analyser schneller sind als dieser. dann
                 * beenden die anderen analyzer ihre arbeit vor diesem und bekommen neue arbeit. da jeder analyzer
                 * diese arbeit bekommt, bekommt auch dieser noch arbeitende sie. aus diesem grund wird sein semaphor
                 * um 1 erhöht.
                 * Das ist auch nicht schlimm, da das semaphor ja nur dazu da ist, den process schlafen zu legen falls
                 * noch keine arbeit da ist
                 */
                throw new RuntimeException("Fatal error in Analyzer Thread. Too many available permits in semaphore! Something has gone terribly wrong!");
            }
            this.waitHandleForMessageChunk.release();
        }
    }
}
