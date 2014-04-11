package org.haw.cas.RealTimeCore.CommonTypes.Entities;


import org.haw.cas.GlobalTypes.MessageInfo.Provenance;
import org.haw.cas.GlobalTypes.MessageInfo.TypeOfNeed;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA.
 * User: j-wil_000
 * Date: 27.10.13
 * Time: 00:11
 *
 * This class represents the functional entity "Bedarf".
 */
@Entity
public class Need implements IAbstractEntity {

    private static final long serialVersionUID  = 65687;

    @Id
    private long id;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.ORDINAL)
    private TypeOfNeed type;
    @AttributeOverrides({
            @AttributeOverride(name="latitude", column=@Column(nullable=true)),
            @AttributeOverride(name="longitude", column=@Column(nullable=true))
    })
    private GeoCoordinate geoCoordiante;
    private LocalDateTime creationTime;
    private String message;
    @Enumerated(EnumType.ORDINAL)
    private Provenance provenance;

    protected Need() {}

    public Need(User user, TypeOfNeed type, GeoCoordinate geoCoordiante, LocalDateTime creationTime, String message, Provenance provenance) {
        this.provenance = provenance;
        this.id = -1;
        this.user = user;
        this.type = type;
        this.geoCoordiante = geoCoordiante;
        this.creationTime = creationTime;
        this.message = message;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public TypeOfNeed getType() {
        return type;
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
        return "Need{" +
                "message='" + message + '\'' +
                "type='" + type + '\'' +
                '}';
    }

    public Provenance getProvenance() {
        return provenance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Need)) return false;

        Need need = (Need) o;

        if (id != need.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
