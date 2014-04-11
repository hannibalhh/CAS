package org.haw.cas.RealTimeCore.CommonTypes.Entities;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 04.11.13
 * Time: 17:56
 * <p/>
 * This is the general interface for any entity.
 */
@Entity
public interface IAbstractEntity extends Serializable {

    public long getId();

    public void setId(long id);
}
