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
		checkAndCreateLayout("Basic", "/views/organizer_session/basic.xhtml");
		checkAndCreateLayout("Advanced", "/views/organizer_session/advanced.xhtml");
		checkAndCreateLayout("Advanced+", "/views/organizer_session/advancedPlus.xhtml");
		checkAndCreateLayout("Elite", "/views/organizer_session/elite.xhtml");
		checkAndCreateLayout("Elite+", "/views/organizer_session/elitePlus.xhtml");
		checkAndCreateLayout("Elite Pro", "/views/organizer_session/elitePro.xhtml");
	}

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
	
	public Layout getLayoutByName(String name) {
	    TypedQuery<Layout> query = em.createQuery("SELECT l FROM Layout l WHERE l.name = :name", Layout.class);
	    query.setParameter("name", name);
	    List<Layout> layouts = query.getResultList();

	    if (!layouts.isEmpty()) {
	        return layouts.get(0);
	    }

	    return null;
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
