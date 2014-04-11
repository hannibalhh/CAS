package org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages;

import org.haw.cas.GlobalTypes.MessageInfo.TypeOfInfo;

/**
 * Created with IntelliJ IDEA.
 * User: Matle Eckhoff
 * Date: 22.10.13
 * Time: 19:17
 * To change this template use File | Settings | File Templates.
 */
public class LocationMessage extends InfoMessage {
    private double longitude;
    private double latitude;

    public double getLongitude() {
        return longitude;

    }

    public double getLatitude() {
        return latitude;
    }

    //in the CommonData GeoCoordinate the constructor is called with double latitude, double longitude. This might leads to confusion.
    public LocationMessage(double latitude, double longitude, int certainty) {
        super(certainty);
        this.longitude = longitude;
        this.latitude = latitude;
        this.typeOfInfo = TypeOfInfo.Location;
    }

    @Override
    public String toString() {
        return "LocationMessage{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", certainty=" + certainty +
                '}';
    }
}
