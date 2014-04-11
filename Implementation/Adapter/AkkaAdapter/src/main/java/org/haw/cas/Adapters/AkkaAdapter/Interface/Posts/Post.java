package org.haw.cas.Adapters.AkkaAdapter.Interface.Posts;

import org.haw.cas.GlobalTypes.MessageInfo.Provenance;

import java.time.LocalDateTime;

/**
 * Created by j-wil_000 on 08.12.13.
 */
public class Post {

    private String author;
    private LocalDateTime creationTime;
    private String message;
    private Provenance provenance;
    private String pictureUrl;

    public Post(String author, LocalDateTime creationTime, String message, Provenance provenance, String pictureUrl) {
        this.author = author;
        this.creationTime = creationTime;
        this.message = message;
        this.provenance = provenance;
        this.pictureUrl = pictureUrl;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getMessage() {
        return message;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }
}
