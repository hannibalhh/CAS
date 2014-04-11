package org.haw.cas.guiadapter.handler.receiver;

import java.io.IOException;

import org.haw.cas.guiadapter.data.config.Config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

/**
 * 
 * Receiver for messages from GUI to RTC.
 * 
 * @author alex
 * 
 */

public class RTCGuiReceive implements IRTCGuiReceive {

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer;
	
	private final Config config;

	public RTCGuiReceive(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void openChannel(String host, Integer port, String queueName) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setPort(port);
			factory.setUsername(config.getUser());
			factory.setPassword(config.getPassword());
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(queueName, true, false, false, null);
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeChannel() {
		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Delivery receiveNext() {

		if (consumer != null) {
			try {
				return consumer.nextDelivery();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// TODO notify consumer null
		}

		return null;
	}

}
