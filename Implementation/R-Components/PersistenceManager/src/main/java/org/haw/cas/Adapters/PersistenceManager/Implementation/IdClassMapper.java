package org.haw.cas.Adapters.PersistenceManager.Implementation;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 25.11.13
 * Time: 17:50
 * This Class persist the latest id for an entity class
 */

@Entity
@Table(name = "IDCLASSMAPPER")
public class IdClassMapper implements Serializable {

    @Id
    private Class Class;
    private long id;

    public IdClassMapper() {
    }

    public IdClassMapper(Class Class, long id) {
        this.Class = Class;
        this.id = id;
    }

    public Class getEntityClass() {
        return Class;
    }

    public long getId() {
        return id;
    }

    public void incrementId() {
        this.id++;

    }

}
