package org.haw.cas.Adapters.AkkaAdapter.Implementation;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.Adapters.RabbitMQAdapter.Implementation.RabbitMQAdapter;
import org.haw.cas.Adapters.RabbitMQAdapter.Interface.IRabbitMQAdapter;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * User: Jason Wilmans & Christian Hüning
 * Date: 22.10.13
 * Time: 21:13
 * <p/>
 * This adapter connects the RTC with the GUI.
 * Do not use this in the other direction, since the msqQueues would then be twisted.
 */
public class AkkaAdapterComponent implements IAkkaAdapter {

    /**
     * Instance of RtcDataMinerHandler, which sends and/or receive Data via
     * Queue.
     */
    private final IRabbitMQAdapter rabbitMQAdapter;
    private final AppSettings appSettings;
    private final Logger logger = Logger.getLogger(AkkaAdapterComponent.class);

    private HashMap<Class<? extends IAkkaMessage>, List<Consumer<? extends IAkkaMessage>>> msgTypToListenerMap;

    public AkkaAdapterComponent() {
        this.msgTypToListenerMap = new HashMap<>();
        rabbitMQAdapter = new RabbitMQAdapter();
        appSettings = new AppSettings();
        try {
            if (!appSettings.getBoolean("testing")) {
                rabbitMQAdapter.subscribeForMessage(this::listenToQueue, appSettings.getString("GUItoRTCQueue"));
            } else {   //Testing == true
                rabbitMQAdapter.subscribeForMessage(this::listenToQueue, appSettings.getString("GUItoRTCQueueTest"));
            }

        } catch (SettingException e) {
            e.printStackTrace();
        }

        logger.debug("initialized");
    }

    /**
     * The ListenerMethod which is provided to the RabbitMQAdapter. Will be called when a new message arrives
     *
     * @param msg the received message as a byte[]. Needs to be transformed into a functional type via Protobuff.
     */
    private void listenToQueue(byte[] msg) {

        try {
            IAkkaMessage message = AkkaMessageConvert.convert(msg);

            if (msgTypToListenerMap.containsKey(message.getClass())) {
                msgTypToListenerMap.get(message.getClass())
                        .forEach(
                                (c) -> ((Consumer<IAkkaMessage>) c).accept(message)
                        );
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void send(IAkkaMessage message) throws TechnicalProblemException, ConfigurationException {
        try {
            if (!appSettings.getBoolean("testing")) {
                rabbitMQAdapter.sendMessage(message.getBytes(), appSettings.getString("RTCtoGUIQueue"));
            } else { //testing == true
                rabbitMQAdapter.sendMessage(message.getBytes(), appSettings.getString("RTCtoGUIQueueTest"));
            }

        } catch (SettingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <M extends IAkkaMessage> void subscribeForMessage(Class<M> messageType, Consumer<M> messageListener) {
        if (msgTypToListenerMap.containsKey(messageType)) {
            msgTypToListenerMap.get(messageType).add(messageListener);
        } else {
            List<Consumer<? extends IAkkaMessage>> list = new CopyOnWriteArrayList<>();
            list.add(messageListener);
            msgTypToListenerMap.put(messageType, list);
        }
    }
}
