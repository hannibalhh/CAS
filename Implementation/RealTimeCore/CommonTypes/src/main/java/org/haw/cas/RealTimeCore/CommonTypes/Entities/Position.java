package org.haw.cas.RealTimeCore.CommonTypes.Entities;


import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian & Jason
 * Date: 27.10.13
 * Time: 11:33
 * <p/>
 *
 */
@Entity
public class Position implements IAbstractEntity {

    private static final long serialVersionUID = 54979L;

    @Id
    @GenericGenerator(name="positions", strategy="increment")
    @GeneratedValue(generator="positions")
    private long id;

    private GeoCoordinate geoCoordinate;
    private LocalDateTime date;
    private int certainty;
    @Enumerated(EnumType.ORDINAL)
    private Provenance provenance;
    private String message;

    protected Position() {}

    public Position(GeoCoordinate geoCoordinate, LocalDateTime date, int certainty, Provenance provenance, String message) {
        this.id = 0;
        this.geoCoordinate = geoCoordinate;
        this.date = date;
        this.certainty = certainty;
        this.provenance = provenance;
        this.message = message;
    }

    public GeoCoordinate getGeoCoordinate() {
        return geoCoordinate;
    }

    public void setGeoCoordinate(GeoCoordinate geoCoordinate) {
        this.geoCoordinate = geoCoordinate;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public Provenance getProvenance() {
        return provenance;
    }

    public String getMessage() {
        return message;
    }

    public int getCertainty() {
        return certainty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;

        Position position = (Position) o;

        if (id != position.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}