package org.haw.cas.Adapters.PersistenceManager.Implementation.Exception;

import org.haw.cas.RealTimeCore.CommonTypes.Entities.IAbstractEntity;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 08.11.13
 * Time: 15:12
 * To change this template use File .
 */
public class CantFoundEntityException extends Throwable {
    public<T extends IAbstractEntity> CantFoundEntityException(T t) {
        super("can't find an object with the given id "+ t.getId() + " from class "  + t.getClass().getSimpleName());
    }


    public <T extends IAbstractEntity> CantFoundEntityException(Class<T> entityClassType, Integer integer) {
        super("can't find an object with the given id "+integer + " from class "  + entityClassType.getSimpleName());
    }
}
