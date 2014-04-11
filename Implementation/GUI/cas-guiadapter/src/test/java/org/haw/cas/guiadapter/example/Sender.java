package org.haw.cas.guiadapter.example;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds;
import org.haw.cas.guiadapter.data.config.Config;
import org.haw.cas.guiadapter.handler.sender.RTCGuiSender;

public class Sender {

	public static void main(String[] args) {
		RTCGuiSender sender = new RTCGuiSender(Config.localhost);
		sender.openChannel(Config.localhost.host, Config.localhost.port, "cas_rtc_to_gui");
		sender.sendMessage(UpdateNeeds.newBuilder().build().toByteArray());
		sender.sendMessage(UpdateNeeds.newBuilder().build().toByteArray());
		sender.sendMessage(UpdateNeeds.newBuilder().build().toByteArray());
		sender.closeChannel();
	}

}
