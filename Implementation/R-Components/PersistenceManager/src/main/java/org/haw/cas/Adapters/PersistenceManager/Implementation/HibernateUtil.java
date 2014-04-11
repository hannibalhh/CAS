package org.haw.cas.Adapters.PersistenceManager.Implementation;

import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;



/**
 * Created with IntelliJ IDEA.
 * User: Nils
 * Date: 25.11.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;


    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {

            try {
                // Create the SessionFactory from standard (hibernate.cfg.xml) config file.
                Configuration configuration = new Configuration().configure();
                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                // Log the exception.
                System.err.println("Initial SessionFactory creation failed." + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }


        return sessionFactory;
    }

}
