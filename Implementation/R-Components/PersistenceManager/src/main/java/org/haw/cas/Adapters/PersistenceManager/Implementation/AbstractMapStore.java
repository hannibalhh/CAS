package org.haw.cas.Adapters.PersistenceManager.Implementation;

import com.hazelcast.core.MapStore;
import org.apache.log4j.Logger;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 24.11.13
 * Time: 16:36
 * This class Provides an abstract JPA implementation for the Hazelcast maps.
 */
public abstract class AbstractMapStore<K extends Serializable, V> implements MapStore<K, V> {

    protected static final Logger logger = Logger.getLogger(AbstractMapStore.class);
    private Session session;

    public AbstractMapStore() {
        session = HibernateUtil.getSessionFactory().openSession();
    }

    @Override
    public void store(K k, V v) {
        logger.debug("Storing " + "(" + k + "," + v + ")");
        executeTransaction(s -> {
            try {
                s.saveOrUpdate(v);
            } catch (NonUniqueObjectException e) {
                s.merge(v);
            }
        });
    }

    @Override
    public void storeAll(Map<K, V> kvMap) {
        executeTransaction((session) -> {
            for (Map.Entry<K, V> e : kvMap.entrySet()) {
                logger.debug("Storing all " + "(" + e.getValue() + ")");
                V tmp = null;
                try {
                    session.saveOrUpdate(e.getValue());
                } catch (NonUniqueObjectException x) {
                    session.merge(e.getValue());
                }
            }
        });
    }

    /**
     * Allows the execution of a hibernate related transaction. Manages all errors that could occur and the
     * session/transaction management. DO NOT interfere with this management by using session.close(),
     * session.beginTransaction() or similar usages.
     *
     * @param transaction the transaction's code
     * @param <T>
     * @return the transaction's result value, (transaction successfull) ? transaction code's return : null
     */
    protected <T> T executeTransactionResult(ITransaction<T> transaction) {
        Transaction tx = session.beginTransaction();
        try {
            T t = transaction.exec(session);
            tx.commit();
            return t;
        } catch (Throwable e) {
            if (tx != null) tx.rollback();
            session.close();
            session = HibernateUtil.getSessionFactory().openSession();

            logger.error("An exception occurred during a transaction.", e);
            return null;
        }
    }

    /**
     * Allows the execution of a hibernate related transaction. Manages all errors that could occur and the
     * session/transaction management. DO NOT interfere with this management by using session.close(),
     * session.beginTransaction() or similar usages.
     *
     * @param transaction the transaction's code
     */
    protected void executeTransaction(ISimpleTransaction transaction) {
        Transaction tx = session.beginTransaction();
        try {
            transaction.exec(session);
            session.getTransaction().commit();
        } catch (Throwable e) {
            if (tx != null) tx.rollback();
            session.close();
            session = HibernateUtil.getSessionFactory().openSession();

            logger.error("An exception occurred during a transaction.", e);
        }
    }

    @FunctionalInterface
    protected interface ITransaction<T> {
        T exec(Session session);
    }

    @FunctionalInterface
    protected interface ISimpleTransaction {
        void exec(Session session);
    }
}
