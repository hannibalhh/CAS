package org.haw.cas.Adapters.AkkaAdapter.Implementation;

import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Crevasses.UpdateCrevassesMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Needs.UpdateNeedsMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions.UpdateUserPositionsMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.PositionSetting;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.TimelineRequest;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.TimelineType;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.UpdateTimelinesMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Posts.UpdatePostsMessage;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.Need;

import java.time.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * User: Jason Wilmans
 * Date: 27.10.13
 * Time: 02:51
 * <p/>
 * This class converts protobuf bytestreams back to normal AkkaAdapterMessages.
 */
public class AkkaMessageConvert {

    private static Logger logger = Logger.getLogger(AkkaMessageConvert.class);

    public static IAkkaMessage convert(byte[] proto) throws InvalidProtocolBufferException {
        AkkaMessage.AkkaMessageBuilder.Builder builder = AkkaMessage.AkkaMessageBuilder.newBuilder();

        builder.mergeFrom(proto);

        switch (builder.getType()) {

            case UpdateNeedsMessage:
                return new UpdateNeedsMessage(builder.getSender());

            case UpdatePostsMessage:
                return new UpdatePostsMessage();

            case UpdateTimelinesMessage:
                List<TimelineRequest> requests =
                        builder.getUpdateTimelines()
                                .getRequestListList().stream()
                                .map(r -> new TimelineRequest(
                                        TimelineType.values()[r.getType().getNumber()-1],
                                        PositionSetting.values()[r.getPositionSetting().getNumber()-1])
                                )
                                .collect(Collectors.toList());

                return new UpdateTimelinesMessage(builder.getUpdateTimelines().getRequestId(),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(builder.getUpdateTimelines().getFrom()), ZoneOffset.ofHours(1)),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(builder.getUpdateTimelines().getTo()), ZoneOffset.ofHours(1)),
                        requests
                );

            case UpdateUserPositionsMessage:
                return new UpdateUserPositionsMessage(builder.getSender(),
                        builder.getUpdateUserPositions().getRequestId(),
                        new GeoCoordinate(builder.getUpdateUserPositions().getCenter().getLatitude(),
                                builder.getUpdateUserPositions().getCenter().getLongitude()),
                        builder.getUpdateUserPositions().getRadius(),
                        LocalDateTime.ofInstant(Instant.ofEpochMilli(builder.getUpdateUserPositions().getOldest()), ZoneOffset.ofHours(1)));

            case UpdateCrevassesMessage:
                return new UpdateCrevassesMessage();

            default:
                logger.warn("Encountered an unknown message type: " + builder.getType());
                return null;
        }
    }
}
