package triphub.dao.customization;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;
import java.util.List;
import triphub.entity.subscription.Layout;

/**
 * DAO for managing database layouts related to the user subscription model.
 */
@Named("databaseLayoutDAO")
@ApplicationScoped
public class DatabaseLayoutDAO {

    @PersistenceContext
    private EntityManager em;

    /**
     * Default constructor.
     */
    public DatabaseLayoutDAO() {}

    /**
     * Initializes the layout configuration with a predefined set.
     */
    public void initializeLayouts() {
        checkAndCreateLayout("Basic", "/views/organizer_session/basic.xhtml");
        checkAndCreateLayout("Advanced", "/views/organizer_session/advanced.xhtml");
        checkAndCreateLayout("Advanced+", "/views/organizer_session/advancedPlus.xhtml");
        checkAndCreateLayout("Elite", "/views/organizer_session/elite.xhtml");
        checkAndCreateLayout("Elite+", "/views/organizer_session/elitePlus.xhtml");
        checkAndCreateLayout("Elite Pro", "/views/organizer_session/elitePro.xhtml");
    }

    /**
     * Checks if a layout with the given name exists in the database. If not, creates a new one with the given path.
     *
     * @param layoutName The name of the layout.
     * @param xhtmlPath  The path to the XHTML file for the layout.
     */
    private void checkAndCreateLayout(String layoutName, String xhtmlPath) {
        TypedQuery<Layout> query = em.createQuery("SELECT l FROM Layout l WHERE l.name = :name", Layout.class);
        query.setParameter("name", layoutName);
        List<Layout> layouts = query.getResultList();

        Layout layout;

        if (layouts.isEmpty()) {
            layout = new Layout();
        } else {
            layout = layouts.get(0);
        }

        layout.setName(layoutName);
        layout.setXhtmlFile(xhtmlPath);

        if (layouts.isEmpty()) {
            em.persist(layout);
        } else {
            em.merge(layout);
        }
    }

    /**
     * Retrieves a layout based on its name.
     *
     * @param name The name of the layout to retrieve.
     * @return The Layout object if found, otherwise null.
     */
    public Layout getLayoutByName(String name) {
        TypedQuery<Layout> query = em.createQuery("SELECT l FROM Layout l WHERE l.name = :name", Layout.class);
        query.setParameter("name", name);
        List<Layout> layouts = query.getResultList();

        if (!layouts.isEmpty()) {
            return layouts.get(0);
        }

        return null;
    }

    /**
     * Fetches all layouts stored in the database.
     *
     * @return List of all Layouts.
     */
    public List<Layout> getAllLayouts() {
        TypedQuery<Layout> query = em.createQuery("SELECT l FROM Layout l", Layout.class);
        return query.getResultList();
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    /**
     * Retrieves a Layout based on its unique ID.
     *
     * @param id The unique identifier of the layout to retrieve.
     * @return The Layout object if found, otherwise null.
     */
    public Layout find(Long id) {
        return em.find(Layout.class, id);
    }
}
