package triphub.dao.customization;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.*;

import triphub.entity.subscription.*;

@Named("databaseLayoutDAO")
@ApplicationScoped
public class DatabaseLayoutDAO {

    @PersistenceContext
    private EntityManager em;

    
    
    public DatabaseLayoutDAO() {
	}

	public void initializeLayouts() {
        checkAndCreateLayout("Basic", "/triphub/views/organizer_session/basic.xhtml");
        checkAndCreateLayout("Advanced", "/triphub/views/organizer_session/advanced.xhtml");
        checkAndCreateLayout("Advanced+", "/triphub/views/organizer_session/advancedPlus.xhtml");
        checkAndCreateLayout("Elite", "/triphub/views/organizer_session/elite.xhtml");
        checkAndCreateLayout("Elite+", "/triphub/views/organizer_session/elitePlus.xhtml");
        checkAndCreateLayout("Elite Pro", "/triphub/views/organizer_session/elitePro.xhtml");
    }

	private void checkAndCreateLayout(String layoutName, String xhtmlPath) {
	    TypedQuery<Layout> query = em.createQuery("SELECT l FROM Layout l WHERE l.name = :name", Layout.class);
	    query.setParameter("name", layoutName);
	    List<Layout> layouts = query.getResultList();
	    
	    if (layouts.isEmpty()) {
	        Layout layout = new Layout();
	        layout.setName(layoutName);
	        layout.setXhtmlFile(xhtmlPath);
	        em.persist(layout);
	    }
	}
    
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
	
	public Layout find(Long id) {
	    return em.find(Layout.class, id);
	}
    
    
}
