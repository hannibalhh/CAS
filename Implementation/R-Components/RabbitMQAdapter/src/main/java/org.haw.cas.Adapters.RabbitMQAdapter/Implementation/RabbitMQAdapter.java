package org.haw.cas.Adapters.RabbitMQAdapter.Implementation;


import org.haw.cas.Adapters.RabbitMQAdapter.Interface.IRabbitMQAdapter;
import org.haw.cas.GlobalTypes.Settings.AppSettings;

import java.util.function.Consumer;

public class RabbitMQAdapter implements IRabbitMQAdapter {

	private final AppSettings appSettings;

	public RabbitMQAdapter() {
		this.appSettings = new AppSettings();
	}


    @Override
    public void subscribeForMessage(Consumer<byte[]> listener, String queueName) {

        ReceiverMessageThread thread = new ReceiverMessageThread(listener, queueName);
        thread.start();
    }

    @Override
    public void sendMessage(byte[] msg, String queueName) {
        // send this message
        RabbitMQSender sender = new RabbitMQSender();
        sender.openChannel(queueName);
        sender.sendMessage(msg);

        // and close channel
        sender.closeChannel();
    }
}
