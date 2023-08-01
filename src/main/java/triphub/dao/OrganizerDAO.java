package triphub.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;
import triphub.entity.user.*;
import triphub.entity.util.Address;
import triphub.entity.util.Administration;
import triphub.entity.util.CompanyInfo;
import triphub.entity.util.FinanceInfo;
import triphub.entity.util.Picture;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class OrganizerDAO {

	@PersistenceUnit
	private EntityManager em;

	public OrganizerDAO() {
	}

	public OrganizerDAO(EntityManager em) {
		this.em = em;
	}

	public Organizer createOrganizer(UserViewModel form) {

		// Create user
		User user = new User();
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setPhoneNum(form.getPhoneNum());
		user.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
		// Create address
		Address address = new Address();
		address.setNum(form.getNum());
		address.setStreet(form.getStreet());
		address.setCity(form.getCity());
		address.setState(form.getState());
		address.setCountry(form.getCountry());
		address.setZipCode(form.getZipCode());
		user.setAddress(address);
		// Create finance info
		FinanceInfo finance = new FinanceInfo();
		finance.setCCNumber(form.getCCNumber());
		finance.setExpirationDate(form.getExpirationDate());
		user.setFinance(finance);
		// Create company info
		CompanyInfo companyInfo = new CompanyInfo();
		companyInfo.setName(form.getCompanyName());

		// Set logo
		Picture logo = new Picture();
		logo.setLink(form.getCompanyLogoLink());
		companyInfo.setLogo(logo);

		// Set company picture
		Picture picture = new Picture();
		picture.setLink(form.getCompanyPictureLink());
		companyInfo.setPicture(picture);

		// Create administration info
		Administration administration = new Administration();
		administration.setSiret(form.getSiret());
		administration.setPhone(form.getPhone());
		administration.setSector(form.getSector());
		administration.setEmail(form.getAdminEmail());
		
		// Subscription info
		// Add Subscription properties

		// Create provider
		Organizer organizer = new Organizer();
		organizer.setUser(user);
		organizer.setCompanyInfo(companyInfo);
		organizer.setAdministration(administration);
		
		
		em.persist(companyInfo);
		em.persist(administration);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(organizer);
		return organizer;
	}

    public Organizer readOrganizer(Long id) {
        return em.find(Organizer.class, id);
    }
    
    public void deleteOrganizer(Long id) {
        Organizer organizer = readOrganizer(id);
        if (organizer != null) {
            em.remove(organizer);
        }
    }
    
    public void updateOrganizer(Long id) {
        Organizer organizer = readOrganizer(id);
        if (organizer != null) {
            em.merge(organizer);
        }
    }
    
    public Organizer findByEmailOrganizer(String email) throws RegistrationException {
        TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user.email = :email", Organizer.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RegistrationException("Organizer with email " + email + " not found.");
        }
    }
	
	public Organizer findByUserOrganizer(User user) {
	    try {
	        TypedQuery<Organizer> query = em.createQuery("SELECT c FROM Organizer c WHERE c.user = :user", Organizer.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
