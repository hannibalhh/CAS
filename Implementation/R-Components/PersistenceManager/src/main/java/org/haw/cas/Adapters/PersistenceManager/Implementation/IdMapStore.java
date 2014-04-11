package org.haw.cas.Adapters.PersistenceManager.Implementation;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.ObjectNotFoundException;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 25.11.13
 * Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
public class IdMapStore<T> extends AbstractMapStore<Class<T>, IdClassMapper> {

    private static final Logger logger = Logger.getLogger(IdMapStore.class);

    @Override
    public IdClassMapper load(Class<T> clazz) {
        return executeTransactionResult(session -> {
            try {
                IdClassMapper t = (IdClassMapper) session.load(IdClassMapper.class, clazz);
                Hibernate.initialize(t);
                return t;
            } catch (ObjectNotFoundException e) {
                return null;
            }
        });
    }

    @Override
    public Map<Class<T>, IdClassMapper> loadAll(Collection<Class<T>> classes) {

        return executeTransactionResult(session -> {
            Map<Class<T>, IdClassMapper> resultMap = new HashMap<>();
            IdClassMapper t;
            for (Class<T> id : classes) {
                try {
                    t = (IdClassMapper) session.load(IdClassMapper.class, id);
                    Hibernate.initialize(t);
                    resultMap.put(id, t);
                } catch (ObjectNotFoundException e) {
                }
            }
            return resultMap;
        });
    }

    @Override
    public Set<Class<T>> loadAllKeys() {

        logger.debug("try to load all keys");
        return executeTransactionResult(session -> {
            List<IdClassMapper> mapperList = new ArrayList<>();
            Set<Class<T>> resultSet = new HashSet<>();
            mapperList = session.createCriteria(IdClassMapper.class).list();

            for (IdClassMapper idClassMapper : mapperList) {
                resultSet.add(idClassMapper.getEntityClass());
            }

            return resultSet;
        });
    }

    @Override
    public void delete(Class<T> tClass) {
        logger.debug("Deleting " + "(" + tClass + ")");
        executeTransaction((session) -> {
            IdClassMapper classMapper = (IdClassMapper) session.load(IdClassMapper.class, tClass);
            session.delete(classMapper);
        });
    }

    @Override
    public void deleteAll(Collection<Class<T>> tClasses) {

        executeTransaction((session) -> {
            for (Class<T> tClass : tClasses) {
                logger.debug("Deleting all " + "(" + tClass + ")");
                IdClassMapper classMapper = (IdClassMapper) session.load(IdClassMapper.class, tClass);
                session.delete(classMapper);
            }
        });
    }
}
