package org.haw.cas.Adapters.PersistenceManager.Implementation.Exception;


import org.haw.cas.RealTimeCore.CommonTypes.Entities.IAbstractEntity;

/**
 * Thrown if an entity of type T already exists
 */
public class EntityAlreadyExistException extends Throwable {
    public <T extends IAbstractEntity> EntityAlreadyExistException(T t) {
        super("The entity with id " + t.getId() + "already exists");
    }
}
