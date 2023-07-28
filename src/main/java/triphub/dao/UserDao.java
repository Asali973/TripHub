package triphub.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.User;

//@Stateless
	public class UserDao {
	//	@PersistenceContext
		private EntityManager entityManager;

	    public UserDao() {
	    	 this.entityManager = HibernateUtil.createEntityManager();
	    }

	    // CREATE 
	    public void createUser(User user) {
	        EntityTransaction tx = this.entityManager.getTransaction();
	        try {
	            tx.begin();
	            this.entityManager.persist(user);
	            
	            this.entityManager.flush();
	            tx.commit();
	        } catch (Exception e) {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    // READ 
	    public User getUserById(Long id) {
	        return entityManager.find(User.class, id);
	    }

	    public List<User> getAllUsers() {
	        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
	        return query.getResultList();
	    }

	    // UPDATE 
	    public void updateUser(User user) {
	        EntityTransaction tx = entityManager.getTransaction();
	        try {
	            tx.begin();
	            entityManager.merge(user);
	            tx.commit();
	        } catch (Exception e) {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    // DELETE 
	    public void deleteUser(Long id) {
	        EntityTransaction tx = entityManager.getTransaction();
	        try {
	            tx.begin();
	            User user = entityManager.find(User.class, id);
	            if (user != null) {
	                entityManager.remove(user);
	            }
	            tx.commit();
	        } catch (Exception e) {
	            if (tx.isActive()) {
	                tx.rollback();
	            }
	            e.printStackTrace();
	        }
	    }
	    public void closeEntityManager() {
	        entityManager.close();
	    }

	}

