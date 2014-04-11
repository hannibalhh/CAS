package org.haw.cas.guiadapter.example;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds;
import org.haw.cas.guiadapter.AbstractGUIAdapter;
import org.haw.cas.guiadapter.data.config.Config;

import com.google.protobuf.InvalidProtocolBufferException;

public class Receiver extends AbstractGUIAdapter{

	public static void main(String[] args) {
		new Receiver();
	}

	@Override
	protected Config config() {
		return Config.localhost;
	}

	@Override
	public byte[] getMessage(byte[] request) {
		try {
			System.out.println("test: " + UpdateNeeds.parseFrom(request).getClass().getSimpleName());
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
	}

}
