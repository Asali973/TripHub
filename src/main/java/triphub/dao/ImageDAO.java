package triphub.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.product.Image;

@Stateless
public class ImageDAO {
	@PersistenceUnit
	 private EntityManager em;

    public ImageDAO() {
    }

    public ImageDAO(EntityManager em) {
        this.em = em;
    }

    public Image create(Image image) {
        em.persist(image);
        return image;
    }

    public Image read(Long id) {
        return em.find(Image.class, id);
    }

    public Image update(Image image) {
        return em.merge(image);
    }

    public void delete(Long id) {
        Image image = em.find(Image.class, id);
        if (image != null) {
            em.remove(image);
        }
    }

    public List<Image> getAllImages() {
        TypedQuery<Image> query = em.createQuery("SELECT i FROM Image i", Image.class);
        return query.getResultList();
    }
}