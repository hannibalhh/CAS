package org.haw.cas.Adapters.RabbitMQAdapter.Implementation;

import java.io.IOException;



import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

/**
 * 
 * Receiver for messages from GUI to RTC.
 * 
 * @author alex
 * 
 */

public class RabbitMQReceiver {

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer;

	private final AppSettings appSettings;

    public RabbitMQReceiver() {
        appSettings = new AppSettings();
    }


	public void openChannel(String queueName) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(appSettings.getString("RabbitMQAddress"));
			factory.setPort(appSettings.getInt("RabbitMQPort"));
			factory.setUsername(appSettings.getString("RabbitMQUser"));
			factory.setPassword(appSettings.getString("RabbitMQPassword"));
			connection = factory.newConnection();
			channel = connection.createChannel();

			channel.queueDeclare(queueName, true, false, false, null);
			consumer = new QueueingConsumer(channel);
			channel.basicConsume(queueName, true, consumer);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SettingException e) {
            e.printStackTrace();
        }
    }


	public void closeChannel() {
		try {
			channel.close();
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


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
