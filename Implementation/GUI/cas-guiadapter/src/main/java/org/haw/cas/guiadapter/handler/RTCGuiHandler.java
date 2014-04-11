package org.haw.cas.guiadapter.handler;

import org.haw.cas.guiadapter.IGUIAdapter;
import org.haw.cas.guiadapter.data.config.Config;
import org.haw.cas.guiadapter.data.config.Queue;
import org.haw.cas.guiadapter.handler.receiver.IRTCGuiReceive;
import org.haw.cas.guiadapter.handler.receiver.RTCGuiReceive;
import org.haw.cas.guiadapter.handler.sender.RTCGuiSender;

import com.google.protobuf.InvalidProtocolBufferException;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class RTCGuiHandler implements IRTCGuiHandler {

	private final IGUIAdapter adapter;
	private final Config config;

	public RTCGuiHandler(IGUIAdapter adapter) {
		super();
		this.config = Config.official;
		System.out.println(config);
		this.adapter = adapter;	
		startLogic();	
	}
	
	public RTCGuiHandler(IGUIAdapter adapter,Config c) {
		super();
		System.out.println(c);
		this.adapter = adapter;
		this.config = c;
		startLogic();
	}


	private void startLogic() {
		IRTCGuiReceive receiverNeeds = new RTCGuiReceive(config);
		receiverNeeds.openChannel(config.getHost(), config.getPort(),
				Queue.mailbox_user_needs_web.name());

		while (true) {
			// get message
			Delivery next = receiverNeeds.receiveNext();
			// send answer
			RTCGuiSender sender = new RTCGuiSender(config);

			
			// TODO right message as value
			byte[] m = adapter.getMessage(next.getBody());

		}
	}

	@Override
	public void sendToAll(byte[] message) {
		RTCGuiSender sender = new RTCGuiSender(config);
		sender.openChannel(config.getHost(), config.getPort(), Queue.mailbox_user_needs_web.name());
		sender.sendMessage(message);
	}

}
