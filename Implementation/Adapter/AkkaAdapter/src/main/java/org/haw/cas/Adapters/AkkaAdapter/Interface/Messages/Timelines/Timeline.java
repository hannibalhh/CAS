package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Jason Wilmans
 * Date: 25.11.13
 * Time: 18:15
 * <p/>
 * This class defines a timeline of data points x to y.
 */
public class Timeline implements Iterable<DataPoint> {

    private final List<DataPoint> dataPoints;
    private final TimelineType type;

    public Timeline(TimelineType type) {
        this(new LinkedList<>(), type);
    }

    public Timeline(List<DataPoint> dataPoints, TimelineType type) {
        this.dataPoints = dataPoints;
        this.type = type;
    }

    @Override
    public Iterator<DataPoint> iterator() {
        return dataPoints.iterator();
    }

    public static Timeline fromCreatorFunction(TimelineType type,
                                           LocalDateTime from,
                                           LocalDateTime to,
                                           int pointsPerTimeline,
                                           PositionSetting positionSetting,
                                           IValueCreator valueCreator) {

        Timeline result = new Timeline(type);

        Duration intervalLength = Duration.between(from, to).dividedBy(pointsPerTimeline);
        LocalDateTime intervalStart = from;
        LocalDateTime intervalEnd = from.plus(intervalLength);

        for (int i = 0; i <= pointsPerTimeline; i++) {
            result.dataPoints.add(new DataPoint(intervalStart.toInstant(ZoneOffset.ofHours(1)).toEpochMilli(), valueCreator.getIntervalValue(intervalStart, intervalEnd, positionSetting)));
            intervalStart = intervalStart.plus(intervalLength);
            intervalEnd = intervalEnd.plus(intervalLength);
        }

        return result;
    }

    public TimelineType getType() {
        return type;
    }

    @FunctionalInterface
    public interface IValueCreator {
        public float getIntervalValue(LocalDateTime from, LocalDateTime to, PositionSetting positionSetting);
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "dataPoints=" + dataPoints +
                ", type=" + type +
                '}';
    }
}
