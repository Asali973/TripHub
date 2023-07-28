package triphub.dao;

import javax.persistence.EntityManager;
import triphub.entity.user.Organizer;

public class OrganizerDAO {
    private EntityManager em;

    public OrganizerDAO(EntityManager em) {
        this.em = em;
    }

    public Organizer create(Organizer organizer) {
        em.persist(organizer);
        return organizer;
    }

    public Organizer read(Long id) {
        return em.find(Organizer.class, id);
    }
}
