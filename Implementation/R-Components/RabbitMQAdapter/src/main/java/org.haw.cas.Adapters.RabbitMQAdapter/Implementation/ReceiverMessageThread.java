package org.haw.cas.Adapters.RabbitMQAdapter.Implementation;

import com.rabbitmq.client.QueueingConsumer.Delivery;
import org.apache.log4j.Logger;


import java.util.function.Consumer;

public class ReceiverMessageThread extends Thread {

	private final Consumer<byte[]> receiver;
    private String queueName;
    private static Logger logger = Logger.getLogger(ReceiverMessageThread.class);


    public ReceiverMessageThread(Consumer<byte[]> receiver, String queueName) {
		this.receiver = receiver;
        this.queueName = queueName;
    }

	@Override
	public void run() {
		RabbitMQReceiver receiverMessage = new RabbitMQReceiver();
		receiverMessage.openChannel(queueName);

		while (true) {
			Delivery next = receiverMessage.receiveNext();
            try {
                receiver.accept(next.getBody());
            } catch(Throwable ex){
                logger.error(ex.getMessage(), ex);
            }
		}
	}

}
