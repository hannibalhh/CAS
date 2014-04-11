package org.haw.cas.StatisticsComponent.Implementation.UseCases;

import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.PositionSetting;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.Timeline;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.TimelineType;

import java.time.LocalDateTime;

/**
 * User: Jason Wilmans
 * Date: 26.11.13
 * Time: 18:47
 * <p/>
 * This interface must be implemented by classes that want to generate timeLines.
 */
public interface ITimelineCreator {
    /**
     * This method is called to create timeleines.
     *
     * @return
     */
    public float createTimeLine(LocalDateTime from, LocalDateTime to, PositionSetting positionSetting);
}
