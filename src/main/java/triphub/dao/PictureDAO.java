package triphub.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import triphub.entity.util.Picture;

@Stateless
public class PictureDAO {
	@PersistenceContext
	private EntityManager em;

	public PictureDAO() {
	}

	public PictureDAO(EntityManager em) {
		this.em = em;
	}

	public Picture create(Picture Picture) {
		em.persist(Picture);
		return Picture;
	}

	public Picture read(Long id) {
		return em.find(Picture.class, id);
	}

	public Picture update(Picture Picture) {
		return em.merge(Picture);
	}

	public void delete(Long id) {
		Picture Picture = em.find(Picture.class, id);
		if (Picture != null) {
			em.remove(Picture);
		}
	}

	public List<Picture> getAllPictures() {
		TypedQuery<Picture> query = em.createQuery("SELECT i FROM Picture i", Picture.class);
		return query.getResultList();
	}
}