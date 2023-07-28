package triphub.dao;

import javax.persistence.EntityManager;
import triphub.entity.user.SuperAdmin;

public class SuperAdminDAO {
    private EntityManager em;

    public SuperAdminDAO(EntityManager em) {
        this.em = em;
    }

    public SuperAdmin create(SuperAdmin superAdmin) {
        em.persist(superAdmin);
        return superAdmin;
    }

    public SuperAdmin read(Long id) {
        return em.find(SuperAdmin.class, id);
    }
}

