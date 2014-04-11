package org.haw.cas.StatisticsComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.PositionSetting;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;

import java.time.LocalDateTime;

/**
 * User: Jason Wilmans
 * Date: 26.11.13
 * Time: 19:36
 */
public class NeedsTimelineCreator implements ITimelineCreator {
    private final INeedsComponent needsComponent;
    private final TypeOfNeed type;
    private static final Logger logger = Logger.getLogger(NeedsTimelineCreator.class);

    public NeedsTimelineCreator(INeedsComponent needsComponent, TypeOfNeed type) {

        this.needsComponent = needsComponent;
        this.type = type;
    }

    @Override
    public float createTimeLine(LocalDateTime from, LocalDateTime to, PositionSetting positionSetting) {
        switch (positionSetting) {
            case With:
                return needsComponent.getNeedCountWithPosition(type, from, to);
            case Without:
                return needsComponent.getNeedCountWithoutPosition(type, from, to);
            case Both:
                return needsComponent.getNeedCount(type, from, to);
            default:
                logger.error("Encountered unknown positions setting: " + positionSetting);
                return 0;
        }
    }
}
