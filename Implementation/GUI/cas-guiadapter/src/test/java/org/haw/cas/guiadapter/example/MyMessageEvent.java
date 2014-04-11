package org.haw.cas.guiadapter.example;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds;

import com.google.protobuf.Message;


public class MyMessageEvent implements MessageEvent{	
	
	@Override
	public UpdateNeeds message() {
		return UpdateNeeds.newBuilder().build();
	}

	@Override
	public void answer(Message mss) {
		System.out.println("you anwers");
	}

	@Override
	public String toString() {
		return "MyMessageEvent [message()=" + message() + "]";
	}

	
	

}
