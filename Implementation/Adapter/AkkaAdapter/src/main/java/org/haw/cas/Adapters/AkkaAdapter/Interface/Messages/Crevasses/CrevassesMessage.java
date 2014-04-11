package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Crevasses;


import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Crevasse;

import java.sql.Timestamp;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: dude
 * Date: 31.10.13
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
public class CrevassesMessage implements IAkkaMessage {
    private AkkaMessage.AkkaMessageBuilder akkaMessage;

    public CrevassesMessage(List<Crevasse> crevasses) {
        this("broadcast", crevasses);
    }

    // First build the crevasses message and then put it into the general akka message and save it for deserialization
    public CrevassesMessage(String sender, List<Crevasse> crevasses) {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses.Builder crevassesMessageBuilder =
                org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasses.newBuilder();
        crevasses.forEach((n) -> {
            crevassesMessageBuilder.addCrevasse(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.CrevassesMessage.Crevasse.newBuilder()
                    .setAuthor(n.getUser().getAliases().iterator().next())
                    .setProvenance(n.getProvenance().toString()) //TODO
                    .setGeo(GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                            .setLatitude((float) n.getGeoCoordiante().getLatitude())
                            .setLongitude((float) n.getGeoCoordiante().getLongitude())

                            .build())
                    .setCreationTime(Timestamp.valueOf(n.getCreationTime()).getTime())
                    .setMessage(n.getMessage())
                    .build()
            );
        });
        akkaMessage = AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender(sender)
                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.CrevassesMessage)
                .setCrevasses(crevassesMessageBuilder.build())

                .build();
    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {



        return "CrevassesMessage{" +
                "akkaMessage=" +
                akkaMessage.getCrevasses().getCrevasseList().toString()
                +
                '}';
    }
}
