package triphub.dao.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

import javax.persistence.TypedQuery;

import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.product.service.accommodation.AccommodationType;
import triphub.entity.util.Address;
import triphub.viewModel.SubServicesViewModel;

@Stateless
public class AccommodationDAO {

	@PersistenceContext
	private EntityManager em;

	public AccommodationDAO(EntityManager em) {
		this.em = em;

	}

	public AccommodationDAO() {
	}

	public Accommodation create(SubServicesViewModel accommodationVm) {

		// creer a Accommodation
		Accommodation accommodation = new Accommodation();

		accommodation.setName(accommodationVm.getName());
		accommodation.setDescription(accommodationVm.getDescription());
		accommodation.setAccommodationType(accommodationVm.getAccommodationType());

//		Picture picture = new Picture();
//		picture.setLink(formService.getLink());
//		accommodation.setPicture(picture);

		Address addressAccommodation = new Address();
		addressAccommodation.setNum(accommodationVm.getAddress().getNum());
		addressAccommodation.setStreet(accommodationVm.getAddress().getStreet());
		addressAccommodation.setCity(accommodationVm.getAddress().getCity());
		addressAccommodation.setState(accommodationVm.getAddress().getState());
		addressAccommodation.setCountry(accommodationVm.getAddress().getCountry());
		accommodation.setAddress(addressAccommodation);

//		em.persist(picture);
		em.persist(addressAccommodation);
		em.persist(accommodation);

		accommodation.setAddress(addressAccommodation);

		return accommodation;
	}

	public Accommodation read(Long id) {
		return em.find(Accommodation.class, id);
	}

	// Méthode pour modifer une entité Accommodation
	public void update(Accommodation accommodation) {
		if (accommodation != null) {
			// Mettre à jour l'entité dans la base de données
			em.merge(accommodation);

		}

	}

	// Méthode pour supprimer une entité Accommodation en utilisant son id
	public void delete(Long id) {
		// Rechercher l'entité Accommodation par son id
		Accommodation accommodationToDelete = em.find(Accommodation.class, id);

		// Vérifier si l'entité existe
		if (accommodationToDelete != null) {
			// Supprimer l'entité de la base de données
			em.remove(accommodationToDelete);
		}
	}

	public Accommodation findAccommodationByName(String nameAccommodation) {
		TypedQuery<Accommodation> query = em.createQuery(
				"SELECT a FROM Accommodation a WHERE a.nameAccommodation = :nameAccommodation", Accommodation.class);
		query.setParameter("nameAccommodation", nameAccommodation);

		return query.getSingleResult();

	}

	public List<Accommodation> findByType(AccommodationType AccommodationType) {
		TypedQuery<Accommodation> query = em.createQuery(
				"SELECT a FROM Accommodation a WHERE a.accommodation = :accommodation", Accommodation.class);
		query.setParameter("accommodation", AccommodationType);
		return query.getResultList();
	}

	public List<Accommodation> getAllAccommodation() {
		TypedQuery<Accommodation> query = em.createQuery("SELECT t FROM Transportation t", Accommodation.class);
		return query.getResultList();
	}

}
