package DataTypes;

import DataTypes.DataTypeInterfaces.ILocation;
import org.haw.cas.Adapters.RTCToDataMinerAdapter.Interface.Messages.LocationMessage;

/**
 * Created with IntelliJ IDEA.
 * User: N3trunner
 * Date: 21.10.13
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
public class Location implements ILocation {

    private double latitude;
    private double longitude;

    private int certainty = 0;

    public int getCertainty() {
        return certainty;
    }

    public void setCertainty(int certainty) {
        this.certainty = certainty;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(double latitude, double longitude, int certainty) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.certainty = certainty;
    }

    @Override
    public LocationMessage getMessage() {
        return new LocationMessage(this.getLatitude(), this.getLongitude(), this.certainty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.latitude, latitude) != 0) return false;
        if (Double.compare(location.longitude, longitude) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", certainty=" + certainty +
                '}';
    }
}
