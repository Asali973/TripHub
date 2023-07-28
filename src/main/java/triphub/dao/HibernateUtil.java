package triphub.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
private static EntityManagerFactory entityManagerFactory;
	
	private HibernateUtil() {
	}
	
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("triphub");
    }

    public static EntityManager createEntityManager() {
    	return entityManagerFactory.createEntityManager();
    }
    
    public static void closeEntityManagerFactory() {
    	entityManagerFactory.close();
    }

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public static void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
		HibernateUtil.entityManagerFactory = entityManagerFactory;
	}
    
}
