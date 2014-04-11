package org.haw.cas.Adapters.PersistenceManager.Interface;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 04.11.13
 * Time: 17:56
 * <p/>
 * This is the general interface for any entity.
 */
public interface
        IAbstractEntity extends Serializable {



    public long getId();

    public void setId(long id);
}
