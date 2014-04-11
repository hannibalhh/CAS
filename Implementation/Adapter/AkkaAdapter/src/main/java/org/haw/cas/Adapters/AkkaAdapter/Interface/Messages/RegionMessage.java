package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;

public class RegionMessage implements IAkkaMessage {

    private final AkkaMessage.AkkaMessageBuilder akkaMessage;

    public RegionMessage(String urlToKml) {
        this("broadcast", urlToKml);
    }

    public RegionMessage(String sender, String urlToKml) {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.RegionMessage.Region.Builder regionMessageBuilder = org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.RegionMessage.Region
                .newBuilder().setUrlToKMl(urlToKml);

        akkaMessage = AkkaMessage.AkkaMessageBuilder
                .newBuilder()
                .setSender(sender)
                .setType(
                        AkkaMessage.AkkaMessageBuilder.MessageType.RegionMessage)
                .setRegion(regionMessageBuilder.build()).build();
    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {
        return "RegionMessage{" +
                "akkaMessage=" + akkaMessage +
                '}';
    }
}
