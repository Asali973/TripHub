package triphub.dao.user;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import triphub.entity.user.Customer;
import triphub.entity.user.SuperAdmin;
import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.entity.util.Picture;
import triphub.helpers.PasswordUtils;
import triphub.helpers.RegistrationException;
import triphub.viewModel.UserViewModel;

/**
 * DAO class responsible for operations related to the SuperAdmin entity.
 */
@Stateless
public class SuperAdminDAO {

	@PersistenceContext
	private EntityManager em;

	public SuperAdminDAO() {
	}

	/**
	 * Creates a SuperAdmin entity based on the provided form.
	 *
	 * @param form the UserViewModel form.
	 * @return the created SuperAdmin entity.
	 */
	public SuperAdmin createSuperAdmin(UserViewModel form) {

		User user = User.createUserFromViewModel(form);
		Address address = Address.createAddressFromViewModel(form);
		user.setAddress(address);

		FinanceInfo finance = FinanceInfo.createFinanceInfoFromViewModel(form);
		user.setFinance(finance);

		Picture pictureProfile = new Picture();
		pictureProfile.setLink(form.getProfilePicture());

		SuperAdmin superAdmin = new SuperAdmin();
		superAdmin.setId(form.getSuperAdminId());
		superAdmin.setUser(user);
		superAdmin.setPicture(pictureProfile);

		em.persist(pictureProfile);
		em.persist(finance);
		em.persist(address);
		em.persist(user);
		em.persist(superAdmin);
		em.flush();
		return superAdmin;
	}

	/**
	 * Updates the SuperAdmin entity based on the provided userViewModel.
	 *
	 * @param userViewModel the form for updating.
	 * @return the updated UserViewModel.
	 */
	public UserViewModel updateSuperAdmin(UserViewModel userViewModel) {
		SuperAdmin superAdmin = em.find(SuperAdmin.class, userViewModel.getSuperAdminId());

		if (superAdmin == null) {
			return null;
		}

		superAdmin.updateSuperAdminFromViewModel(userViewModel);

		em.persist(superAdmin);
		em.flush();

		return userViewModel;
	}

	/**
	 * Initializes a SuperAdmin from its ID.
	 *
	 * @param superAdminId the ID of the SuperAdmin.
	 * @return the initialized UserViewModel.
	 */
	public UserViewModel initSuperAdmin(Long superAdminId) {
		SuperAdmin superAdmin = em.find(SuperAdmin.class, superAdminId);
		if (superAdmin == null) {
			return null;
		}

		return superAdmin.initSuperAdminViewModel();
	}

	/**
	 * Retrieves a SuperAdmin entity by its ID.
	 *
	 * @param id the ID of the SuperAdmin.
	 * @return the SuperAdmin entity.
	 */
	public SuperAdmin readSuperAdmin(Long id) {
		return em.find(SuperAdmin.class, id);
	}

	/**
	 * Deletes a SuperAdmin entity by its ID.
	 *
	 * @param id the ID of the SuperAdmin.
	 */
	public void deleteSuperAdmin(Long id) {
		SuperAdmin superAdmin = em.find(SuperAdmin.class, id);
		if (superAdmin != null) {
			em.remove(superAdmin);
		}
	}

	/**
	 * Finds a SuperAdmin based on its email.
	 *
	 * @param email the email of the SuperAdmin.
	 * @return the matching SuperAdmin, or null if not found.
	 */
	public SuperAdmin findByEmailSuperAdmin(String email) {
		TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user.email = :email",
				SuperAdmin.class);
		query.setParameter("email", email);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Finds a SuperAdmin based on the associated User entity.
	 *
	 * @param user the associated User entity.
	 * @return the matching SuperAdmin, or null if not found.
	 */
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

	/**
	 * Finds a SuperAdmin based on the ID of its associated User entity.
	 *
	 * @param userId the ID of the associated User entity.
	 * @return the matching SuperAdmin, or null if not found.
	 */
	public SuperAdmin findSuperAdminByUserId(Long userId) {
		TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c WHERE c.user.id = :userId",
				SuperAdmin.class);
		query.setParameter("userId", userId);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Retrieves all SuperAdmins.
	 *
	 * @return a list of all SuperAdmins.
	 */
	public List<SuperAdmin> findAllSuperAdmins() {
		TypedQuery<SuperAdmin> query = em.createQuery("SELECT c FROM SuperAdmin c", SuperAdmin.class);
		return query.getResultList();
	}

}
