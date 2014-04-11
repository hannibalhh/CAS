package org.haw.cas.DataMiningTools.TextMining;


import org.haw.cas.RealTimeCore.CommonTypes.Entities.GeoCoordinate;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 22.11.13
 * Time: 13:27
 * Calculates the distance between two latitude and longitude points.
 * Typical inaccuracy are about 0.3%
 */
public class HarversineFormula implements IGeoCoordinateDistanceCalculator {

    /**
     *
     * @param pos1 the latitude and longitude point of position 1
     * @param pos2 the latitude and longitude point of position 2
     * @return returns the Distance between pos1 and pos1 in KM
     */
    @Override
    public double calculateDistance(GeoCoordinate pos1, GeoCoordinate pos2) {
        double R = 6371; // Radius of the earth
        double latitudeDistance =toRad(pos2.getLatitude() - pos1.getLatitude());
        double longitudeDistance =toRad(pos2.getLongitude() - pos1.getLongitude());

        double lat1 = toRad(pos1.getLatitude());
        double lat2 = toRad(pos2.getLatitude());

        double a =  Math.sin(latitudeDistance/2)*Math.sin(latitudeDistance/2) +
                    Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) *
                    Math.sin(longitudeDistance/2) * Math.sin(longitudeDistance/2);

        double c = 2* Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R*c;
    }

    private double toRad(double number){
        return number* Math.PI/180;
    }

}
