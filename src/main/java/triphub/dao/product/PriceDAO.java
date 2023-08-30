package triphub.dao.product;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.Price;

/**
 * DAO for managing operations related to Price entities.
 */
@Stateless
public class PriceDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor.
     */
    public PriceDAO() {}

    /**
     * Constructor allowing dependency injection of an EntityManager.
     * 
     * @param em The EntityManager to be used in this DAO.
     */
    public PriceDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists a new price entity to the database.
     * 
     * @param price The price to be persisted.
     * @return The persisted price.
     */
    public Price create(Price price) {
        em.persist(price);
        return price;
    }

    /**
     * Fetches a price by its ID.
     * 
     * @param id The ID of the price to be fetched.
     * @return The fetched price, or null if not found.
     */
    public Price read(Long id) {
        return em.find(Price.class, id);
    }

    /**
     * Updates an existing price in the database.
     * 
     * @param price The price with updated details.
     * @return The updated price.
     */
    public Price update(Price price) {
        return em.merge(price);
    }

    /**
     * Removes a price from the database using its ID.
     * 
     * @param id The ID of the price to be removed.
     */
    public void delete(Long id) {
        Price price = em.find(Price.class, id);
        if (price != null) {
            em.remove(price);
        }
    }

    /**
     * Fetches all prices stored in the database.
     * 
     * @return A list of all prices.
     */
    public List<Price> getAllPrices() {
        TypedQuery<Price> query = em.createQuery("SELECT p FROM Price p", Price.class);
        return query.getResultList();
    }
}
