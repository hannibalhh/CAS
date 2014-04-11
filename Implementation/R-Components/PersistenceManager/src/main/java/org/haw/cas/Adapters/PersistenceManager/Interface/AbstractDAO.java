package org.haw.cas.Adapters.PersistenceManager.Interface;


import org.haw.cas.Adapters.PersistenceManager.Implementation.DatabaseServices;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.CantFoundEntityException;
import org.haw.cas.Adapters.PersistenceManager.Implementation.Exception.EntityAlreadyExistException;
import org.haw.cas.Adapters.PersistenceManager.Implementation.IdClassMapper;
import org.haw.cas.GlobalTypes.Settings.AppSettings;
import org.haw.cas.GlobalTypes.Settings.SettingException;
import org.haw.cas.RealTimeCore.CommonTypes.Entities.IAbstractEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nils & Jason
 * Date: 15.10.13
 * Time: 07:45
 * <p/>
 * This class is the template for any DAOs that rely on relational persistence.
 */
public abstract class AbstractDAO<T extends IAbstractEntity> {

    private static List<String> firstInitializeListForEntities;
    protected Map<Long, T> entityMap;
    private Map<Class<T>, IdClassMapper> idCounterMap;

    protected AbstractDAO(Class<T> entityClass) {

        String entityMapName = entityClass.getName();
        try {
            //set cluster to testing cluster if testing is true
            if (new AppSettings().getBoolean("testing")) {
                //Adds all TestMaps in the Cluster <entity>testing
                entityMapName = entityMapName + "testing";
            }
            //creates a hazelcastmap
        } catch (SettingException e) {
            e.printStackTrace();
        }

        this.entityMap = DatabaseServices.getNewEntityMap(entityMapName);


        //reset the map if testing is true and it's the first DAO with the given entityMapName
        //in case that several DAOs for the same entity are initialized the map will be cleared just once.
        try {
            if (new AppSettings().getBoolean("testing")) {
                boolean containsString = false;
                for (String s : getFirstInitializeListForEntities()) {
                    if (s.equals(entityMapName)) containsString = true;
                    break;
                }

                if (!containsString) {
                    this.entityMap.clear();
                    //System.out.println("reset");
                    getFirstInitializeListForEntities().add(entityMapName);
                    //System.out.println("after add "+ getFirstInitializeListForEntities());
                }
            }
        } catch (SettingException e) {
            e.printStackTrace();
        }

        this.idCounterMap = DatabaseServices.getNewIdEntityMap("idCounterMap");
    }

    private static List<String> getFirstInitializeListForEntities() {
        if (firstInitializeListForEntities == null) {
            firstInitializeListForEntities = new ArrayList<>();
        }
        return firstInitializeListForEntities;
    }

    /**
     * Generates an unique ID for an Entity of the given Class.
     *
     * @param tClass Class object of the entity
     * @return an unique ID
     */
    private long generateIdForClass(Class<T> tClass) {
        if (!idCounterMap.containsKey(tClass)) {
            idCounterMap.put(tClass, new IdClassMapper(tClass, 0L));
        }

        IdClassMapper result = idCounterMap.get(tClass);
        result.incrementId();

        idCounterMap.put(tClass, result);

        //System.out.println("new id " +result +  " for class" +tClass.getSimpleName()  );

        return result.getId();

    }

    /**
     * Returns the Entity with the given id  or null if the entity does not exist.
     *
     * @param id of the entity
     * @return Entity or null
     */
    public T getById(long id) {
        return entityMap.get(id);
    }

    /**
     * creates a new entity. if an entity with the same id already exist an exception is thrown
     *
     * @param t
     * @throws EntityAlreadyExistException if an key with the id entity.getId() exist
     */
    public void create(T t) throws EntityAlreadyExistException {
        if (entityMap.containsKey(t.getId()) && t.getId() > 0) {
            throw new EntityAlreadyExistException(t);
        }
        long id = generateIdForClass((Class<T>) t.getClass());
        t.setId(id);

        entityMap.put(id, t);
    }

    /**
     * updates an existing entity. if none entity with the key of the given entity exist an exception is thrown
     *
     * @param entity the given entity
     * @throws CantFoundEntityException if no key with the id entity.getId() exist
     */
    public void update(T t) throws CantFoundEntityException {
        if (entityMap.containsKey(t.getId())) {
            entityMap.put(t.getId(), t);
        } else {
            throw new CantFoundEntityException(t);
        }
    }

    public T delete(T t) {
        return entityMap.remove(t.getId());
    }

    public T deleteByID(long id) {
        return entityMap.remove(id);
    }

    public Collection<T> getAll() {

        return entityMap.values();
    }


}
