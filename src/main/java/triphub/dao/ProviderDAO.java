package triphub.dao;

import javax.persistence.EntityManager;
import triphub.entity.user.Provider;

public class ProviderDAO {
    private EntityManager em;

    public ProviderDAO(EntityManager em) {
        this.em = em;
    }

    public Provider create(Provider provider) {
        em.persist(provider);
        return provider;
    }

    public Provider read(Long id) {
        return em.find(Provider.class, id);
    }
}

