package com.galaxii.common.hibernate;

/*
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
*/


public class HibernateUtil {

/*
	private static final String DEFAULT_PERSISTENCE_UNIT = "com.galaxii.hibernate.jpa";
	private static final EntityManagerFactory entityManagerFactory = 
		buildEntityManagerFactory( HibernateUtil.DEFAULT_PERSISTENCE_UNIT );
    private static final ThreadLocal<EntityManager> ENTITY_MANAGERS = 
		new ThreadLocal<EntityManager>();
*/
	/*
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).buildServiceRegistry();
			return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    */
/*
	private static EntityManagerFactory buildEntityManagerFactory( String unit ) {
		return Persistence.createEntityManagerFactory( unit );
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static EntityManagerFactory getEntityManagerFactory( String unit ) {
		return entityManagerFactory;
	}

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGERS.get();
	}

	public static void removeEntityManager() {
		EntityManager em = ENTITY_MANAGERS.get();
		if(em != null) {
			em.close();
		}
		ENTITY_MANAGERS.remove();
	}

	public static void setEntityManager(EntityManager em) {
		HibernateUtil.ENTITY_MANAGERS.set(em);
	}

	public static void closeEntityManagerFactory() {
		if(entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}
*/
}

