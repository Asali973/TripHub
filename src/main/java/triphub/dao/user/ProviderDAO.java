package triphub.dao.user;

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
public class ProviderDAO {

	@PersistenceContext
	private EntityManager em;

	public ProviderDAO() {
	}

//	public ProviderDAO(EntityManager em) {
//		this.em = em;
//	}

	public Provider createProvider(UserViewModel form) {

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

		// Create provider
		Provider provider = new Provider();
		provider.setUser(user);
		provider.setCompanyInfo(companyInfo);
		provider.setAdministration(administration);
		
		
		em.persist(companyInfo);
		em.persist(administration);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(provider);
		return provider;
	}

	public Provider readProvider(Long id) {
		return em.find(Provider.class, id);
	}
	
    public void deleteProvider(Long id) {
        Provider provider = readProvider(id);
        if (provider != null) {
            em.remove(provider);
        }
    }
    
    public void updateProvider(Long id) {
        Provider provider = readProvider(id);
        if (provider != null) {
            em.merge(provider);
        }
    }

    public Provider findByEmailProvider(String email) throws RegistrationException {
        TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user.email = :email", Provider.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RegistrationException("Provider with email " + email + " not found.");
        }
    }
	
	public Provider findByUserProvider(User user) {
	    try {
	        TypedQuery<Provider> query = em.createQuery("SELECT c FROM Provider c WHERE c.user = :user", Provider.class);
	        query.setParameter("user", user);
	        return query.getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}
}
