package DSPersistenceManager.Services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.haw.cas.GlobalTypes.Settings.AppSettings;

import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: sebastian
 * Date: 15.10.13
 * Time: 07:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractDAO {
    protected DatabaseServices dbService;
    Logger logger = LogManager.getLogger(AbstractDAO.class);

    protected <T> T getById(String id, Class<T> tClass){
        EntityManager em = dbService.getEntityManager();
        T result = em.find(tClass, id);
        em.close();
        return result;
    }

    protected <T> void create(T entity){
        EntityManager em = dbService.getEntityManager();
        em.getTransaction().begin();
       // logger.debug("Entity persisted: " + entity);
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    public <T> void update(T entity) throws IllegalArgumentException{
        EntityManager em = dbService.getEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    public <T> void delete(String entityId, Class<T> tClass){
        EntityManager em = dbService.getEntityManager();
        T managedEntity = em.find(tClass, entityId);

        if(managedEntity != null){
            em.getTransaction().begin();
            em.remove(managedEntity);
            em.getTransaction().commit();
            em.close();
         //   logger.debug("Entity removed: " + managedEntity);
        }
    }
}
