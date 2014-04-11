package org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.builder.information.BuildInformation;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.builder.proto.BuildProtoMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.IRTCAdapter;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;
import org.haw.cas.Adapters.RabbitMQAdapter.Implementation.RabbitMQAdapter;
import org.haw.cas.Adapters.RabbitMQAdapter.Interface.IRabbitMQAdapter;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * @author Christian Hüning & Alex
 *         <p/>
 *         This class represents RTCToDataMinerComponent, that encapsulates the
 *         communication between the RTC and the DataMiner.
 */
public class RTCAdapterComponent implements IRTCAdapter {

    /**
     * Instance of RtcDataMinerHandler, which sends and/or receive Data via
     * Queue.
     */
    private final IRabbitMQAdapter rabbitMQAdapter;
    private final AppSettings appSettings;
    private Logger logger = Logger.getLogger(RTCAdapterComponent.class);
    private HashMap<Class<? extends IDataMinerMessage>, List<Consumer<? extends IDataMinerMessage>>> msgTypToListenerMap;

    /**
     * Constructor for this adapter.
     *
     * @param consumer indicates whether or not to consume messages from the Queue
     */
    public RTCAdapterComponent(boolean consumer) {
        this.rabbitMQAdapter = new RabbitMQAdapter();
        this.appSettings = new AppSettings();
        this.msgTypToListenerMap = new HashMap<>();
        if (consumer) {
            try {
                if (!appSettings.getBoolean("testing")) {
                    rabbitMQAdapter.subscribeForMessage(this::listenToQueue, appSettings.getString("DataMinerRTCQueue"));
                } else {         //Testing = true
                    rabbitMQAdapter.subscribeForMessage(this::listenToQueue, appSettings.getString("DataMinerRTCQueueTest"));
                }
            } catch (SettingException e) {
                e.printStackTrace();
            }
        }

        logger.debug("initialized");
    }

    /**
     * Default constructor. Does not listen to the queue!
     */
    public RTCAdapterComponent() {
        this(false);
    }

    /**
     * Listener method for message retrieval.
     *
     * @param msg
     */
    private void listenToQueue(byte[] msg) {

        try {
            org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.Information message = org.haw.cas.Adapters.RTCToDataMinerAdapter.Implementation.data.messageTyp.java.InformationMessage.Information.parseFrom(msg);
            InformationMessage infMessage = BuildInformation.buildInformation(message);

            //Semaphore receiverGate = new Semaphore(0);

            if (msgTypToListenerMap.containsKey(infMessage.getClass())) {
                msgTypToListenerMap.get(infMessage.getClass())
                        .forEach(
                                (c) -> {
                                    ((Consumer<InformationMessage>) c).accept(infMessage);
                                    logger.info("RealTimecore processed an information message");
                                }
                        );
            }
        } catch (InvalidProtocolBufferException | ParseException e) {
            logger.error("Error while listening to queue: " + ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * This method sends an Information Object via the internel Queue.
     */
    @Override
    public void send(InformationMessage information)
            throws TechnicalProblemException, ConfigurationException {

        if (information.getProvenance() == null) {
            throw new IllegalArgumentException("The provenance of the information " + information + " was null");
        }

        try {

            if (!appSettings.getBoolean("testing")) {
                rabbitMQAdapter.sendMessage(BuildProtoMessage.buildProtoInformation(information).toByteArray(), appSettings.getString("DataMinerRTCQueue"));
            } else { //testing = true
                rabbitMQAdapter.sendMessage(BuildProtoMessage.buildProtoInformation(information).toByteArray(), appSettings.getString("DataMinerRTCQueueTest"));
            }
        } catch (SettingException e) {
            logger.error("Error while sending InformationMessage to RTC: " + ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener) {
        if (msgTypToListenerMap.containsKey(messageType)) {
            msgTypToListenerMap.get(messageType).add(messagelistener);
        } else {
            List<Consumer<? extends IDataMinerMessage>> list = new CopyOnWriteArrayList<>();
            list.add(messagelistener);
            msgTypToListenerMap.put(messageType, list);
        }
    }

}
