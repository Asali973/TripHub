package triphub.dao.user;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.TypedQuery;

import triphub.entity.user.User;
import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.viewModel.UserViewModel;

@Stateless
public class UserDAO {
	@PersistenceContext
	private EntityManager em;

//	public UserDAO(EntityManager em) {
//		this.em = em;
//	}
	
	public UserDAO() {
	}

	public User createUser(User user) {
		em.persist(user);
		return user;
	}

	public User readUser(Long id) {
		return em.find(User.class, id);
	}
	
	public UserViewModel initUser(Long userId) {
	    User user = em.find(User.class, userId);
	    if (user == null) {
	        return null;
	    }
	    
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUserId(user.getId());
        userViewModel.setFirstName(user.getFirstName());
        userViewModel.setLastName(user.getLastName());
        userViewModel.setEmail(user.getEmail());
        userViewModel.setPhoneNum(user.getPhoneNum());

        Address address = user.getAddress();
        if (address != null) {
            userViewModel.setNum(address.getNum());
            userViewModel.setStreet(address.getStreet());
            userViewModel.setCity(address.getCity());
            userViewModel.setState(address.getState());
            userViewModel.setCountry(address.getCountry());
            userViewModel.setZipCode(address.getZipCode());
        }

        FinanceInfo finance = user.getFinance();
        if (finance != null) {
            userViewModel.setCCNumber(finance.getCCNumber());
            userViewModel.setExpirationDate(finance.getExpirationDate());
        }
        
        userViewModel.setUserId(user.getId());

	    return userViewModel;
	}

	public User findByEmailUser(String email) {
		TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
		query.setParameter("email", email);

		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
