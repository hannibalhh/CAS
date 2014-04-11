package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

/**
 * User: Jason Wilmans
 * Date: 21.11.13
 * Time: 19:41
 */
public class TimelineRequest {

    private final TimelineType type;
    private final PositionSetting positionSetting;

    public TimelineRequest(TimelineType type, PositionSetting positionSetting) {
        this.type = type;
        this.positionSetting = positionSetting;
    }

    public TimelineType getType() {
        return type;
    }

    public PositionSetting getPositionSetting() {
        return positionSetting;
    }

    @Override
    public String toString() {
        return "TimelineRequest{" +
                "type=" + type +
                ", positionSetting=" + positionSetting +
                '}';
    }
}
