package DSPersistenceManager.Model;

import org.eclipse.persistence.nosql.annotations.DataFormatType;
import org.eclipse.persistence.nosql.annotations.NoSql;

import javax.persistence.Embeddable;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 11.10.13
 * Time: 13:04
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
@NoSql(dataFormat= DataFormatType.MAPPED)
public class Geodata {
    private double latitude;
    private double longitude;


    public Geodata(){}

    public Geodata(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude(){
        return this.longitude;
    }

    public double setLongitude(){
        return this.longitude;
    }

    @Override
    public String toString() {
        return "Geodata{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
