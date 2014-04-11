package org.haw.cas.Adapters.RabbitMQAdapter.Interface;

import java.util.function.Consumer;

/**
 * This adapter encapsulates the technology behind RabbitMQ.
 * The interface uses byte[]'s as parameters since every functional adapter
 * is assumed to use Protobuff as messaging technology.
 * @author Christian Hüning
 */
public interface IRabbitMQAdapter {

    /**
     * Subscribe for an incoming message.
     * NOTE: Calls to listener will be asynchronous!
     * @param listener The Consumer to be called upon message retrieval of a message from queue queueName. Must not be null.
     * @param queueName The Queue which will be listened to. Must not be null.
     */
	public void subscribeForMessage(Consumer<byte[]> listener, String queueName);

    /**
     * Sends a message to the provided queue.
     * @param msg The message to be send as a byte[]. Usually this is a Protobuff encoded message. Not null.
     * @param queueName The name of the queue to send to. Must not be null and valid.
     */
	public void sendMessage(byte[] msg, String queueName);
}
