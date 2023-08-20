package triphub.dao.product;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.product.CartItem;
import triphub.entity.product.TourPackage;
import triphub.entity.product.service.Service;
import triphub.entity.user.User;

@Stateless
public class CartItemDAO {

    @PersistenceContext(unitName = "triphub")
    private EntityManager em;

    public void addToCart(CartItem cartItem) {
        em.persist(cartItem);
        em.flush();
    }
    public void updateCartItem(CartItem cartItem) {
        em.merge(cartItem);
        em.flush();
    }

    public void deleteCartItemById(Long cartItemId) {
        CartItem cartItem = em.find(CartItem.class, cartItemId);
        if (cartItem != null) {
            em.remove(cartItem);
        }
    }

    public List<CartItem> getCartItemsByUser(User user) {
        TypedQuery<CartItem> query = em.createQuery(
            "SELECT ci FROM CartItem ci WHERE ci.user = :user",
            CartItem.class
        );
        query.setParameter("user", user);
        return query.getResultList();
    }
    
    public List<CartItem> getCartItemsWithTourPackages() {
        TypedQuery<CartItem> query = em.createQuery("SELECT c FROM CartItem c", CartItem.class);
        List<CartItem> cartItems = query.getResultList();

        // Populate the selectedTourPackage property for each cart item
        for (CartItem item : cartItems) {
            Long productId = item.getTourPackage().getId();
            TourPackage tourPackage = em.find(TourPackage.class, productId);
            item.setTourPackage(tourPackage);
        }

        return cartItems;
    }
    public CartItem getCartItemByTourPackageAndUser(TourPackage tourPackage, User user) {
        TypedQuery<CartItem> query = em.createQuery(
            "SELECT c FROM CartItem c WHERE c.tourPackage = :tourPackage AND c.user = :user",
            CartItem.class
        );
        query.setParameter("tourPackage", tourPackage);
        query.setParameter("user", user);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public CartItem getCartItemByServiceAndUser(Service service, User user) {
        TypedQuery<CartItem> query = em.createQuery(
            "SELECT c FROM CartItem c WHERE c.user = :user AND c.service = :service",
            CartItem.class
        );
        query.setParameter("user", user);
        query.setParameter("service", service);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
