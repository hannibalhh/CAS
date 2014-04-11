package org.haw.cas.StatisticsComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines.*;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * User: Jason Wilmans
 * Date: 25.11.13
 * Time: 18:02
 */
public class TimelineRequestUseCase {

    private final INeedsComponent needsComponent;
    private final ICrevasseComponent crevasseComponent;
    private final IAkkaAdapter akkaAdapter;
    private final Map<TimelineType, ITimelineCreator> creators;
    private static final Logger logger = Logger.getLogger(TimelineRequestUseCase.class);
    private final static int pointsPerTimeline = 30;


    public TimelineRequestUseCase(INeedsComponent needsComponent, ICrevasseComponent crevasseComponent, IAkkaAdapter akkaAdapter) {
        this.needsComponent = needsComponent;
        this.crevasseComponent = crevasseComponent;
        this.akkaAdapter = akkaAdapter;

        //register creators for the corresponding time line types
        this.creators = new HashMap<>();
        creators.put(TimelineType.Crevasse, new CrevasseTimelineCreator(crevasseComponent));
        creators.put(TimelineType.Accomodation, new NeedsTimelineCreator(needsComponent, TypeOfNeed.Shelter));
        creators.put(TimelineType.Helper, new NeedsTimelineCreator(needsComponent, TypeOfNeed.Helper));
        creators.put(TimelineType.Drink, new NeedsTimelineCreator(needsComponent, TypeOfNeed.Water));
        creators.put(TimelineType.Food, new NeedsTimelineCreator(needsComponent, TypeOfNeed.Food));
        creators.put(TimelineType.Medicaments, new NeedsTimelineCreator(needsComponent, TypeOfNeed.MedicalCare));

        akkaAdapter.subscribeForMessage(UpdateTimelinesMessage.class, this::handleIncomingAkkaMessage);
    }

    /**
     * Checks for incoming requests for timelines, retrieves them from the responsible components, and send the answer
     * to Akka.
     * @param updateTimelinesMessage not null
     */
    public void handleIncomingAkkaMessage(UpdateTimelinesMessage updateTimelinesMessage) {
        logger.debug("received UpdateTimelinesMessage");

        List<Timeline> timelines = updateTimelinesMessage.getRequests().parallelStream()
                .map(request -> Timeline.fromCreatorFunction(
                        request.getType(),
                        updateTimelinesMessage.getFrom(),
                        updateTimelinesMessage.getTo(),
                        pointsPerTimeline,
                        request.getPositionSetting(),
                        creators.get(request.getType())::createTimeLine)
                )
                .collect(Collectors.toList());

        try {
            akkaAdapter.send(new TimelinesMessage(updateTimelinesMessage.getRequestId(), timelines));
            logger.info("TimelineMessages sent");
        } catch (TechnicalProblemException | ConfigurationException e) {
            logger.error("encountered an error sending a timeline", e);
        }
    }
}
