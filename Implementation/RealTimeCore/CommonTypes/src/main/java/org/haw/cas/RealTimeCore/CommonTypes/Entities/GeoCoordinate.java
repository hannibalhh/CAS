package org.haw.cas.RealTimeCore.CommonTypes.Entities;


import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Jason Wilmans
 * Date: 25.10.13
 * Time: 14:25
 */
@Embeddable
public class GeoCoordinate implements Serializable {
    private static final long serialVersionUID = 54979L;

//    @Id
//    private long id;
    private double latitude;
    private double longitude;

    protected GeoCoordinate() {}

    public GeoCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

//    @Override
//    public long getId() {
//        return id;
//    }
//
//    @Override
//    public void setId(long id) {
//        this.id = id;
//    }

    @Override
    public String toString() {
        return "GeoCoordinate{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GeoCoordinate)) return false;

        GeoCoordinate that = (GeoCoordinate) o;

        if (Double.compare(that.latitude, latitude) != 0) return false;
        if (Double.compare(that.longitude, longitude) != 0) return false;

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
}
