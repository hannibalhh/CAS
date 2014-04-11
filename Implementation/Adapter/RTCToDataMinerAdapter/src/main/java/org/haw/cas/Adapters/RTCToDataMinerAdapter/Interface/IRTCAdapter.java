package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface;

import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.IDataMinerMessage;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.InformationMessage;

import java.util.function.Consumer;

/**
 * Created with IntelliJ IDEA.
 * User: Malte, Jason, Christian
 * Date: 21.10.13
 * Time: 17:19
 *
 * This interface describes, how the DataMiner and the RTC can talk to each other.
 */
public interface IRTCAdapter {

    /**
     * Sends the given message to the RealTimeCore.
     *
     * @param information the message to send. Never null
     * @throws TechnicalProblemException if a technical problem occurs, that the adapter could not solve, e.g. connection lost
     * @throws ConfigurationException if the underlying technology was not initialized correctly.
     */
    void send(InformationMessage information) throws TechnicalProblemException, ConfigurationException;

    /**
     * Registers the listener provided in the parameter for the receiving of incoming messages. If a message arrives at
     * the adapter, and a listener registered itself earlier, the message will be passed by calling the listener.
     * NOTE: This call is asynchronous!
     * @param messagelistener A ConsumerMethodReference with a valid MessageType as parameter, never null
     * @param messageType The type of the
     */
    <M extends IDataMinerMessage> void subscribeForInformationMessage(Class<M> messageType, Consumer<M> messagelistener);


}
