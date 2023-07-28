package triphub.dao;
import javax.persistence.EntityManager;

import triphub.entity.user.User;

public class UserDAO {
    private EntityManager em;

    public UserDAO(EntityManager em) {
        this.em = em;
    }

    public User create(User user) {
        em.persist(user);
        return user;
    }

    public User read(Long id) {
        return em.find(User.class, id);
    }

}
