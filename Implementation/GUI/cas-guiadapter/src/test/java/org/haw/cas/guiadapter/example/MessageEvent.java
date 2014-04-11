package org.haw.cas.guiadapter.example;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds;

import com.google.protobuf.Message;

public interface MessageEvent {
	UpdateNeeds message();
	void answer(Message msg);	
}
