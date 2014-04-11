package org.haw.cas.Adapters.RabbitMQAdapter.Implementation;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;

import java.io.IOException;


/**
 * 
 * Sender Class for RTC and GUI.
 * 
 * @author alex & christian
 * 
 */

public class RabbitMQSender {

	private Connection connection;
	private Channel channel;
	private String queueName;
	private final AppSettings appSettings;
    private static final Logger logger = Logger.getLogger(RabbitMQSender.class);

	public RabbitMQSender() {
		super();

        appSettings = new AppSettings();
    }


	public void openChannel(String queueName) {
		try {
			this.queueName = queueName;
			ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(appSettings.getString("RabbitMQAddress"));
            factory.setPort(appSettings.getInt("RabbitMQPort"));
            factory.setUsername(appSettings.getString("RabbitMQUser"));
            factory.setPassword(appSettings.getString("RabbitMQPassword"));
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.queueDeclare(queueName, true, false, false, null);
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

	public Boolean sendMessage(byte[] byteArr) {
		Boolean state = false;


		if (channel != null) {
			try {
				channel.basicPublish("", queueName, null, byteArr);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Throwable ex){
                logger.debug(ex.getMessage(),
                             ex.getCause() == null ? ex : ex.getCause()
                );
            }
		} else {
			// TODO notify channel close
		}

		return state;
	}

}
