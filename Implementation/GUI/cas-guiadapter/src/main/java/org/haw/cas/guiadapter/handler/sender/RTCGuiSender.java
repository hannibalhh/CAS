package org.haw.cas.guiadapter.handler.sender;

import java.io.IOException;

import org.haw.cas.guiadapter.data.config.Config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * Sender Class for RTC and GUI.
 * 
 * @author alex
 * 
 */

public class RTCGuiSender implements IRTCGuiSender {

	private Connection connection;
	private Channel channel;
	private String queueName;
	private final Config config;

	public RTCGuiSender(Config config) {
		super();
		this.config = config;
	}

	@Override
	public void openChannel(String host, Integer port, String queueName) {
		try {
			this.queueName = queueName;
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(host);
			factory.setPort(port);
			factory.setUsername(config.getUser());
			factory.setPassword(config.getPassword());
			connection = factory.newConnection();
			channel = connection.createChannel();
//			channel.queueDelete(queueName);
//			channel.queueDeclare(queueName, true, false, false, null);
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
	public Boolean sendMessage(byte[] byteArr) {
		Boolean state = false;

		if (channel != null) {
			try {
				System.out.println("queueName: " + queueName);
				channel.basicPublish("", queueName, null, byteArr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("notify channel close");
			// TODO notify channel close
		}

		return state;
	}

}
