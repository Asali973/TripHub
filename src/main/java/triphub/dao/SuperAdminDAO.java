package triphub.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.user.SuperAdmin;
import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

@Stateless
public class SuperAdminDAO {

	@PersistenceUnit
	private EntityManager em;

	public SuperAdminDAO() {
	}

	public SuperAdminDAO(EntityManager em) {
		this.em = em;
	}

	public SuperAdmin createSuperAdmin(UserViewModel form) {

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
		// Create SuperAdmin
		SuperAdmin superAdmin = new SuperAdmin();
		superAdmin.setUser(user);
		
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(superAdmin);
		return superAdmin;
	}

	public SuperAdmin readSuperAdmin(Long id) {
		return em.find(SuperAdmin.class, id);
	}
	
    public void deleteSuperAdmin(Long id) {
        SuperAdmin superAdmin = readSuperAdmin(id);
        if (superAdmin != null) {
            em.remove(superAdmin);
        }
    }
    
    public void updateSuperAdmin(Long id) {
        SuperAdmin superAdmin = readSuperAdmin(id);
        if (superAdmin != null) {
            em.merge(superAdmin);
        }
    }

	public SuperAdmin findByEmailSuperAdmin(String email) throws RegistrationException {
		TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user.email = :email",
				SuperAdmin.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			throw new RegistrationException("SuperAdmin with email " + email + " not found.");
		}
	}

	public SuperAdmin findByUserSuperAdmin(User user) {
		try {
			TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user = :user",
					SuperAdmin.class);
			query.setParameter("user", user);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	
}