package triphub.dao.product;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Destination;

/**
 * DAO for managing operations related to Destination entities.
 */
@Stateless
public class DestinationDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor.
     */
    public DestinationDAO() {}

    /**
     * Constructor allowing dependency injection of an EntityManager.
     * 
     * @param em The EntityManager to be used in this DAO.
     */
    public DestinationDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists a new destination entity to the database.
     * 
     * @param destination The destination to be persisted.
     * @return The persisted destination.
     */
    public Destination create(Destination destination) {
        em.persist(destination);
        return destination;
    }

    /**
     * Fetches a destination by its ID.
     * 
     * @param id The ID of the destination to be fetched.
     * @return The fetched destination, or null if not found.
     */
    public Destination read(Long id) {
        return em.find(Destination.class, id);
    }

    /**
     * Updates an existing destination in the database.
     * 
     * @param destination The destination with updated details.
     * @return The updated destination.
     */
    public Destination update(Destination destination) {
        return em.merge(destination);
    }

    /**
     * Removes a destination from the database using its ID.
     * 
     * @param id The ID of the destination to be removed.
     */
    public void delete(Long id) {
        Destination destination = em.find(Destination.class, id);
        if (destination != null) {
            em.remove(destination);
        }
    }

    /**
     * Fetches all destinations stored in the database.
     * 
     * @return A list of all destinations.
     */
    public List<Destination> getAllDestinations() {
        TypedQuery<Destination> query = em.createQuery("SELECT d FROM Destination d", Destination.class);
        return query.getResultList();
    }
}
