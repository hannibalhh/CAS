package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.GeoCoordinatesMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Position;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 17.11.13
 * Time: 23:53
 * This class encapsulates the translation of user positions to akka compatible message.
 */
public class UserPositionsMessage implements IAkkaMessage {

    private AkkaMessage.AkkaMessageBuilder akkaMessage;

    public UserPositionsMessage(String requestId, Iterable<User> users) {
        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPositions.Builder usersBuilder =
                org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPositions.newBuilder();

        usersBuilder.setRequestId(requestId);
        users.forEach(u -> {
            Position lastPosition = u.getLastPosition();

            if (lastPosition != null) {
                usersBuilder.addUserPositions(
                        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.UserPositionsMessage.UserPosition.newBuilder()
                                .setUser(u.getAliases().iterator().next()) //TODO: make one alias the preferred one (the most often used one?)
                                .setCertainty(lastPosition.getCertainty())
                                .setProvenance(lastPosition.getProvenance().toString())
                                .setRadius(5) //TODO: Textminer must provide this!
                                .setCreationTime(lastPosition.getDate().toEpochSecond(ZoneOffset.ofHours(1)))
                                .setCenter(GeoCoordinatesMessage.GeoCoordinates.newBuilder()
                                        .setLatitude((float) lastPosition.getGeoCoordinate().getLatitude())
                                        .setLongitude((float) lastPosition.getGeoCoordinate().getLongitude())
                                        .build())
                                .setMessage(lastPosition.getMessage())
                                .build()
                );
            }
        });

        akkaMessage = AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender("userPositions")
                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.UserPositionsMessage)
                .setUserPositions(usersBuilder.build())
                .build();
    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {
        return akkaMessage.getUserPositions().getUserPositionsList().parallelStream()
                .map(u -> u.getCenter().toString())
                .collect(Collectors.joining());
    }
}
