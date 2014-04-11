package org.haw.cas.StatisticsComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.PositionSetting;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;

import java.time.LocalDateTime;

/**
 * User: Jason Wilmans
 * Date: 26.11.13
 * Time: 18:52
 */
public class CrevasseTimelineCreator implements ITimelineCreator {

    private final ICrevasseComponent crevasseComponent;
    private final static Logger logger = Logger.getLogger(CrevasseTimelineCreator.class);

    public CrevasseTimelineCreator(ICrevasseComponent crevasseComponent) {

        this.crevasseComponent = crevasseComponent;
    }

    @Override
    public float createTimeLine(LocalDateTime from, LocalDateTime to, PositionSetting positionSetting) {
        switch (positionSetting) {
            case With:
                return crevasseComponent.getCrevasseCountWithPosition(from, to);
            case Without:
                return crevasseComponent.getCrevasseCountWithoutPosition(from, to);
            case Both:
                return crevasseComponent.getCrevasseCount(from, to);
            default:
                logger.warn("Encountered unknown position setting");
                return 0.0f;
        }
    }
}
