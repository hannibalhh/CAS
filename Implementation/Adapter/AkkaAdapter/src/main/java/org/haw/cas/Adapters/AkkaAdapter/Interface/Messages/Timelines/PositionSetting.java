package org.haw.cas.Adapters.AkkaAdapter.Interface.Messages.Timelines;

/**
 * User: Jason Wilmans
 * Date: 25.11.13
 * Time: 18:12
 * <p/>
 * Determines the valid options for timelines regarding positions.
 */
public enum PositionSetting {
    With, //search only for data points with positions
    Without, // only messages without position
    Both
}
