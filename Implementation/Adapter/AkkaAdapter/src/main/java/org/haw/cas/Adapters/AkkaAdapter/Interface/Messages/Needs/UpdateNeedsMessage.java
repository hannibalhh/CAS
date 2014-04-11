package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

/**
 * User: Jason Wilmans
 * Date: 24.10.13
 * Time: 12:05
 */
public class UpdateNeedsMessage implements IAkkaMessage {

    private final String sender;

    public UpdateNeedsMessage(String sender) {
        this.sender = sender;
    }

    @Override
    public byte[] getBytes() {
        return AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender(sender)
                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.UpdateNeedsMessage)
                .setUpdateNeeds(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UpdateNeedsMessage.UpdateNeeds.newBuilder()
                        .build())
                .build()
                .toByteArray();
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "UpdateNeedsMessage{" +
                "sender='" + sender + '\'' +
                '}';
    }
}
