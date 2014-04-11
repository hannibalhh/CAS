package org.haw.cas.StatisticsComponent.Implementation.UseCases;

import org.apache.log4j.Logger;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.ConfigurationException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Exceptions.TechnicalProblemException;
import org.haw.cas.Adapters.AkkaAdapter.Interface.IAkkaAdapter;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Posts.Post;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Posts.PostsMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Posts.UpdatePostsMessage;
import org.haw.cas.RealTimeCore.CrevasseComponent.Interface.ICrevasseComponent;
import org.haw.cas.RealTimeCore.NeedsComponent.Interface.INeedsComponent;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by j-wil_000 on 08.12.13.
 */
public class PostsRequestUseCase {

    private final IAkkaAdapter akkaAdapter;
    private final INeedsComponent needsComponent;
    private final ICrevasseComponent crevasseComponent;
    private static final Logger logger = Logger.getLogger(PostsRequestUseCase.class);

    public PostsRequestUseCase(IAkkaAdapter akkaAdapter, INeedsComponent needsComponent, ICrevasseComponent crevasseComponent) {
        this.akkaAdapter = akkaAdapter;
        this.needsComponent = needsComponent;
        this.crevasseComponent = crevasseComponent;

        akkaAdapter.subscribeForMessage(UpdatePostsMessage.class, this::handleIncomingUpdatePostsMessage);
    }

    /**
     * Ia called, when an incoming UpdatePostsMessage is handled. <br/>
     * Collects all messages (within the las 24 hours) without a position and sends a list of those.
     * @param updatePostsMessage the incoming message, != null
     */
    private void handleIncomingUpdatePostsMessage(UpdatePostsMessage updatePostsMessage) {

        logger.debug("received UpdatePostsMessage");

        LocalDateTime day = LocalDateTime.now();
        day.minusDays(1);

        final List<Post> posts = needsComponent.getNeedsWithoutPosition(day, LocalDateTime.now())
        //final List<Post> posts = needsComponent.getAllNeeds()
                .parallelStream()
                .map(n -> new Post(n.getUser().getAliases().iterator().next(),
                        n.getCreationTime(),
                        n.getMessage(),
                        n.getProvenance(),
                        "http://image.spreadshirt.net/image-server/v1/designs/15512474,width=178,height=178/WITH-ME-IS-NOT-GOOD-CHERRY-EATING.png"))
        .collect(Collectors.toList());

        crevasseComponent.getCrevassesWithoutPositions(day, LocalDateTime.now())
            //crevasseComponent.getAllCrevasses()
                .parallelStream()
                .forEach(c -> posts.add(
                        new Post(c.getUser().getAliases().iterator().next(),
                                c.getCreationTime(),
                                c.getMessage(),
                                c.getProvenance(),
                                "http://image.spreadshirt.net/image-server/v1/designs/15512474,width=178,height=178/WITH-ME-IS-NOT-GOOD-CHERRY-EATING.png"))
                        );

        try {
            PostsMessage msg = new PostsMessage(posts);
            akkaAdapter.send(msg);
            logger.info("Answering Akka with a PostsMessage");
        } catch (TechnicalProblemException | ConfigurationException e) {
            logger.error("An error occurred while sending a message to akka.", e);
        }
    }
}
