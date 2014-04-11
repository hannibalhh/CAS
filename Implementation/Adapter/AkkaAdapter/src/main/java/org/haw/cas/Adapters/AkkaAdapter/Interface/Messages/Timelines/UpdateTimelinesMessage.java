package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

import org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.IAkkaMessage;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * User: Jason Wilmans
 * Date: 21.11.13
 * Time: 19:38
 */
public class UpdateTimelinesMessage implements IAkkaMessage {

    private final String requestId;
    private final LocalDateTime from;
    private final LocalDateTime to;
    private final Collection<TimelineRequest> requests;

    public UpdateTimelinesMessage(String sender, LocalDateTime from, LocalDateTime to, Collection<TimelineRequest> requests) {
        this.requestId = sender;
        this.from = from;
        this.to = to;
        this.requests = requests;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    public String getRequestId() {
        return requestId;
    }

    public Collection<TimelineRequest> getRequests() {
        return requests;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "UpdateTimelinesMessage{" +
                "requestId='" + requestId + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", requests=" + requests +
                '}';
    }
}
