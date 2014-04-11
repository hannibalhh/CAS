package org.haw.cas.Adapters.AkkaAdapter.Interface.Posts;

import org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.AkkaMessage;
import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

import java.time.ZoneOffset;
import java.util.stream.Collector;

/**
 * Functional answer to an UpdatePostsMessage
 */
public class PostsMessage implements IAkkaMessage {

    AkkaMessage.AkkaMessageBuilder akkaMessage;

    public PostsMessage(Iterable<Post> posts) {

        org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Posts.Builder postsMessage =
                org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Posts.newBuilder();

        for(Post p : posts) {
            postsMessage.addPosts(
                    org.haw.cas.Adapters.AkkaAdapter.Implementation.ProtoMsgBuilder.PostsMessage.Post.newBuilder()
                            .setAuthor(p.getAuthor())
                            .setCreationTime(p.getCreationTime().toEpochSecond(ZoneOffset.ofHours(1)))
                            .setMessage(p.getMessage())
                            .setPictureUrl(p.getPictureUrl())
                            .setProvenance(p.getProvenance().toString())
                    .build()
            );
        }

        akkaMessage = AkkaMessage.AkkaMessageBuilder.newBuilder()
                .setSender("posts")
                .setType(AkkaMessage.AkkaMessageBuilder.MessageType.PostsMessage)
                .setPosts(postsMessage.build())
                .build();
    }

    @Override
    public byte[] getBytes() {
        return akkaMessage.toByteArray();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        akkaMessage.getPosts().getPostsList().stream()
                .forEach(p -> {
                    result.append("Post{");
                    result.append(p.getAuthor());
                    result.append(", ");
                    result.append(p.getCreationTime());
                    result.append("}, ");
                });

        return "PostsMessage{" +
                "akkaMessage=" +
                        result.toString() +
                '}';
    }
}
