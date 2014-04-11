package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

import java.util.Collection;

/**
 * User: Jason Wilmans
 * Date: 27.11.13
 * Time: 12:41
 */
public class TimelinesMessage implements IAkkaMessage {

    private AkkaMessage.AkkaMessageBuilder akkaMessage;
    private Logger logger = Logger.getLogger(TimelinesMessage.class);

    public TimelinesMessage(String requestId, Collection<Timeline> timelines) {

        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines.Builder timelinesBuilder =
                org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timelines.newBuilder();

        timelinesBuilder.setRequestId(requestId);

        timelines.forEach(t -> {
            org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline.Builder timelineBuilder =
                    org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Timeline.newBuilder();

            timelineBuilder.setType(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.TimelineType.values()[t.getType().ordinal()]);

            timelineBuilder.setHasValues(t.iterator().hasNext());

            t.forEach(tl -> timelineBuilder.addDataPoints(org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.TimelinesMessage.Point.newBuilder()
                    .setX(tl.getX())
                    .setY(tl.getY())
                    .build()));

            timelinesBuilder.addTimelines(timelineBuilder.build());
        });

        akkaMessage = AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender("timelines")
                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.TimelinesMessage)
                .setTimelines(timelinesBuilder.build())
                .build();

    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {
        return "TimelinesMessage{" +
        //        "akkaMessage=" + akkaMessage +
                '}';
    }
}
