package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User: Jason Wilmans
 * Date: 24.10.13
 * Time: 12:05
 */
public class NeedsMessage implements IAkkaMessage {

    private AkkaMessage.AkkaMessageBuilder akkaMessage;
    private static final Logger logger  = Logger.getLogger(NeedsMessage.class);

    public NeedsMessage(List<Need> needs) {
        this("needs", needs);
    }

    public NeedsMessage(String sender, Collection<Need> needs) {

        // First build the needs message and then put it into the general akka message and save it for deserialization

        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Needs.Builder needsMessageBuilder =
                org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Needs.newBuilder();

        needs.forEach((n) -> {
             logger.debug("building a needs message.");
            logger.debug("the provenance of the need is" + n.getProvenance());

            switch (n.getType()) {
                case MedicalCare:
                    needsMessageBuilder. addMedicament(
                            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Medicament.newBuilder()
                                    .setAuthor(n.getUser().getAliases().iterator().next())
                                    .setGeo(
                                            GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                                    .setLatitude((float) n.getGeoCoordiante().getLatitude())
                                                    .setLongitude((float) n.getGeoCoordiante().getLongitude())
                                                    .build())
                                    .setCreationTime(n.getCreationTime().toInstant(ZoneOffset.ofHours(1)).toEpochMilli())
                                    .setMessage(n.getMessage())
                                    .setProvenance(n.getProvenance().toString())
                                    .build()
                    );
                    break;
                case Food:
                    needsMessageBuilder.addFood(
                            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Food.newBuilder()
                                    .setAuthor(n.getUser().getAliases().iterator().next())
                                    .setGeo(
                                            GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                                    .setLatitude((float) n.getGeoCoordiante().getLatitude())
                                                    .setLongitude((float) n.getGeoCoordiante().getLongitude())
                                                    .build())

                                    .setCreationTime(n.getCreationTime().toInstant(ZoneOffset.ofHours(1)).toEpochMilli())
                                    .setMessage(n.getMessage())
                                    .setProvenance(n.getProvenance().toString())
                                    .build()
                    );
                    break;
                case Shelter:
                    needsMessageBuilder.addAccomodation(
                            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Accomodation.newBuilder()
                                    .setAuthor(n.getUser().getAliases().iterator().next())
                                    .setGeo(
                                            GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                                    .setLatitude((float) n.getGeoCoordiante().getLatitude())
                                                    .setLongitude((float) n.getGeoCoordiante().getLongitude())
                                                    .build())
                                    .setCreationTime(n.getCreationTime().toInstant(ZoneOffset.ofHours(1)).toEpochMilli())
                                    .setMessage(n.getMessage())
                                    .setProvenance(n.getProvenance().toString())
                                    .build()
                    );
                    break;
                case Water:
                    needsMessageBuilder.addDrink(
                            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Drink.newBuilder()
                                    .setAuthor(n.getUser().getAliases().iterator().next())
                                    .setGeo(
                                            GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                                    .setLatitude((float) n.getGeoCoordiante().getLatitude())
                                                    .setLongitude((float) n.getGeoCoordiante().getLongitude())
                                                    .build())
                                    .setCreationTime(n.getCreationTime().toInstant(ZoneOffset.ofHours(1)).toEpochMilli())
                                    .setMessage(n.getMessage())
                                    .setProvenance(n.getProvenance().toString())
                                    .build()
                    );
                    break;
                case Helper:
                    needsMessageBuilder.addHelper(
                            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.NeedsMessage.Helper.newBuilder()
                                    .setAuthor(n.getUser().getAliases().iterator().next())
                                    .setGeo(
                                            GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                                    .setLatitude((float) n.getGeoCoordiante().getLatitude())
                                                    .setLongitude((float) n.getGeoCoordiante().getLongitude())
                                                    .build())

                                    .setCreationTime(n.getCreationTime().toInstant(ZoneOffset.ofHours(1)).toEpochMilli())
                                    .setMessage(n.getMessage())
                                    .setProvenance(n.getProvenance().toString())
                                    .build()
                    );
                    break;

                default:
                    Logger.getLogger(NeedsMessage.class).error("unknown needs type in NeedsMessage to Akka: " + n.getType());
                    break;
            }

        });

        akkaMessage = AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender(sender)

                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.NeedsMessage)
                .setNeeds(needsMessageBuilder.build())
                .build();
    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {
        return "NeedsMessage{" +
        akkaMessage.getUserPositions().getUserPositionsList().parallelStream()
                .map(u -> u.getCenter().toString())
                .collect(Collectors.joining())
                + '}';
    }
}
