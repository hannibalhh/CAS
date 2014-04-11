package org.haw.cas.DataMiningTools.TextMining;

import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 22.11.13
 * Time: 14:06
 * <p/>
 * This interface can be implemented by different algorithms to calculate the distance between geographical points.
 */
public interface IGeoCoordinateDistanceCalculator {

    /**
     * Returns the distance in km between pos1 and pos2.
     * @param pos1 not null
     * @param pos2 not null
     * @return
     */
    public double calculateDistance(GeoCoordinate pos1, GeoCoordinate pos2);

}
