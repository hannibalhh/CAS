package org.haw.cas.guiadapter.handler.receiver;

import com.rabbitmq.client.QueueingConsumer.Delivery;

public interface IRTCGuiReceive {

	public void openChannel(String host, Integer port, String queueName);

	public void closeChannel();

	public Delivery receiveNext();

}
