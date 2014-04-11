package org.haw.cas.RealTimeCore.CommonTypes.Entities;


import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.hibernate.cfg.annotations.Nullability;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 30.10.13
 * Time: 15:11
 * <p/>
 * This class implements the functional entity "Deichbruch" of th data model.
 */
@Entity
public class Crevasse implements IAbstractEntity {

    private static final long serialVersionUID  =  78173;

    @Id
    private long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private User user;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="latitude", column=@Column(nullable=true)),
            @AttributeOverride(name="longitude", column=@Column(nullable=true))
    })
    private GeoCoordinate geoCoordiante;
    private LocalDateTime creationTime;
    private String message;
    @Enumerated(EnumType.ORDINAL)
    private Provenance provenance;

    protected Crevasse() {}

    public Crevasse(User user, GeoCoordinate geoCoordiante, LocalDateTime creationTime, String message, Provenance provenance) {
        this.provenance = provenance;
        this.id = -1;
        this.user = user;
        this.geoCoordiante = geoCoordiante;
        this.creationTime = creationTime;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public GeoCoordinate getGeoCoordiante() {
        return geoCoordiante;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Crevasse{" +
                "message='" + message + '\'' +
                '}';
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crevasse)) return false;

        Crevasse crevasse = (Crevasse) o;

        if (id != crevasse.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
