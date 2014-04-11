package org.haw.cas.StatisticsComponent.Implementation;


import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;
import org.haw.cas.StatisticsComponent.Implementation.UseCases.PostsRequestUseCase;
import org.haw.cas.StatisticsComponent.Implementation.UseCases.TimelineRequestUseCase;
import org.haw.cas.StatisticsComponent.Interface.IStatisticsComponent;

/**
 * User: Jason Wilmans
 * Date: 25.11.13
 * Time: 17:45
 * <p/>
 * This class represents the statistics component.
 */
public class StatisticsComponent implements IStatisticsComponent {

    private static final Logger logger = Logger.getLogger(StatisticsComponent.class);
    private final TimelineRequestUseCase timelineRequestUseCase;
    private final PostsRequestUseCase postsRequestUseCase;

    public StatisticsComponent(INeedsComponent needsComponent, ICrevasseComponent crevasseComponent, IAkkaAdapter akkaAdapter) {
        timelineRequestUseCase = new TimelineRequestUseCase(needsComponent, crevasseComponent, akkaAdapter);
        postsRequestUseCase = new PostsRequestUseCase(akkaAdapter, needsComponent, crevasseComponent);
        logger.debug("initialized.");
    }
}
