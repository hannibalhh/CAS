package org.haw.cas.Adapters.PersistenceManager.Implementation;


import org.apache.log4j.Logger;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.IAbstractEntity;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 24.11.13
 * Time: 17:04
 */
public class EntityMapStore<T extends IAbstractEntity> extends AbstractMapStore<Long, T> {

    private Class<T> entityClassType;
    private static final Logger logger = Logger.getLogger(EntityMapStore.class);

    public EntityMapStore(Class<T> tClass) {
        this.entityClassType = tClass;
    }

    @Override
    public T load(Long id) {
        return executeTransactionResult(session -> {
            try {
                T t = (T) session.load(entityClassType, id);
                Hibernate.initialize(t);
                return t;
            } catch(ObjectNotFoundException e) {
                return null;
            }
        });
    }

    @Override
    public Map<Long, T> loadAll(Collection<Long> longs) {

        return executeTransactionResult(session -> {
            Map<Long, T> resultMap = new HashMap<>();
            T t;
            for (Long id : longs) {
                try {
                    t = (T) session.load(entityClassType, id);
                    Hibernate.initialize(t);
                    resultMap.put(id, t);
                } catch (ObjectNotFoundException e) {}
            }
            return resultMap;
        });
    }

    @Override
    public Set<Long> loadAllKeys() {
        logger.debug("try to load all keys");

        return executeTransactionResult(session -> {
            List<IAbstractEntity> entityList = new ArrayList<>();
            Set<Long> resultSet = new HashSet<>();
            entityList = session.createCriteria(entityClassType).list();

            for (IAbstractEntity abstractEntity : entityList) {
                resultSet.add(abstractEntity.getId());
            }

            return resultSet;
        });
    }

    @Override
    public void delete(Long k) {
        logger.debug("Deleting " + "(" + k + ")");
        executeTransaction((session) -> {
            T t = (T) session.load(entityClassType, k);
            session.delete(t);
        });
    }

    @Override
    public void deleteAll(Collection<Long> ks) {

        executeTransaction((session) -> {
            for (Long l : ks) {
                logger.debug("Deleting all " + "(" + l + ")");
                T t = (T) session.load(entityClassType, l);
                session.delete(t);
            }
        });
    }
}
