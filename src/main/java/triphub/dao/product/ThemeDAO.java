package triphub.dao.product;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import triphub.entity.product.Theme;

/**
 * DAO for managing operations related to Theme entities.
 */
@Stateless
public class ThemeDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor.
     */
    public ThemeDAO() {}

    /**
     * Constructor allowing dependency injection of an EntityManager.
     * 
     * @param em The EntityManager to be used in this DAO.
     */
    public ThemeDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * Persists a new theme entity to the database.
     * 
     * @param theme The theme to be persisted.
     * @return The persisted theme.
     */
    public Theme create(Theme theme) {
        em.persist(theme);
        return theme;
    }

    /**
     * Fetches a theme by its ID.
     * 
     * @param id The ID of the theme to be fetched.
     * @return The fetched theme, or null if not found.
     */
    public Theme read(Long id) {
        return em.find(Theme.class, id);
    }

    /**
     * Updates an existing theme in the database.
     * 
     * @param theme The theme with updated details.
     * @return The updated theme.
     */
    public Theme update(Theme theme) {
        return em.merge(theme);
    }

    /**
     * Removes a theme from the database using its ID.
     * 
     * @param id The ID of the theme to be removed.
     */
    public void delete(Long id) {
        Theme theme = em.find(Theme.class, id);
        if (theme != null) {
            em.remove(theme);
        }
    }

    /**
     * Fetches all themes stored in the database.
     * 
     * @return A list of all themes.
     */
    public List<Theme> getAllThemes() {
        TypedQuery<Theme> query = em.createQuery("SELECT t FROM Theme t", Theme.class);
        return query.getResultList();
    }

    /**
     * Searches for a theme by its name.
     * 
     * @param themeName The name of the theme to search for.
     * @return The found theme, or null if not found.
     */
    public Theme findByThemeName(String themeName) {
        TypedQuery<Theme> query = em.createQuery("SELECT t FROM Theme t WHERE t.themeName = :themeName", Theme.class);
        query.setParameter("themeName", themeName);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Searches for an existing theme by its name or creates a new one if it doesn't exist.
     * 
     * @param themeName The name of the theme to search for or create.
     * @return The existing or newly created theme.
     */
    public Theme findOrCreateThemeByName(String themeName) {
        Theme existingTheme = findByThemeName(themeName);

        if (existingTheme == null) {
            Theme newTheme = new Theme();
            newTheme.setThemeName(themeName);
            em.persist(newTheme);
            return newTheme;
        } else {
            return existingTheme;
        }
    }
}
