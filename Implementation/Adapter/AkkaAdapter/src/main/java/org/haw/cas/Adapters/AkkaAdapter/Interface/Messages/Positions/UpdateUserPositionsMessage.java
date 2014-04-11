package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Positions;

import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * User: Jason Wilmans
 * Date: 21.11.13
 * Time: 19:26
 * <p/>
 * This class is a message from the GUI, that requests an update of user positions.
 */
public class UpdateUserPositionsMessage implements IAkkaMessage {

    private final String sender;
    private final String requestId;
    private final GeoCoordinate center;
    private final float radius;
    private final LocalDateTime oldest;

    public UpdateUserPositionsMessage(String sender, String requestId, GeoCoordinate center, float radius, LocalDateTime oldest) {
        this.sender = sender;
        this.requestId = requestId;
        this.center = center;
        this.radius = radius;
        this.oldest = oldest;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public String getRequestId() {
        return requestId;
    }

    public GeoCoordinate getCenter() {
        return center;
    }

    public float getRadius() {
        return radius;
    }

    public LocalDateTime getOldest() {
        return oldest;
    }

    @Override
    public String toString() {
        return "UpdateUserPositionsMessage{" +
                "sender='" + sender + '\'' +
                ", requestId='" + requestId + '\'' +
                ", center=" + center +
                ", radius=" + radius +
                ", oldest=" + oldest +
                '}';
    }
}
