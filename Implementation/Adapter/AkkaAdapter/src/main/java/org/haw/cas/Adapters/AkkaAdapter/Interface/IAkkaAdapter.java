package org.haw.cas.Adapters.AkkaAdapter.Interface;

import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

import java.util.function.Consumer;

/**
 * User: Jason Wilmans, Christian Hüning
 * Date: 22.10.13
 * Time: 21:13
 */
public interface IAkkaAdapter {
    /**
     * Sends the given message to the Akka server.
     *
     * @param message the message to send. Never null
     * @throws TechnicalProblemException if a technical problem occurs, that the adapter could not solve, e.g. connection lost
     * @throws ConfigurationException    if the underlying technology was not initialized correctly.
     */
    void send(IAkkaMessage message) throws TechnicalProblemException, ConfigurationException;

    /**
     * Registers the listener provided in the parameter for the receiving of incoming messages. If a message arrives at
     * the adapter and a listener had registered itself earlier, the message will be passed to him via the method defined
     * in the IMessageListener interface.
     *
     * @param messageListener Never null
     */
    <M extends IAkkaMessage> void subscribeForMessage(Class<M> messageType, Consumer<M> messageListener);
}
