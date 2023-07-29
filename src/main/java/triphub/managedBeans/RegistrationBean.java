package triphub.managedBeans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import triphub.dao.UserDAO;
import triphub.entity.user.User;
import javax.persistence.*;

@ManagedBean
@RequestScoped
public class RegistrationBean {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNum;
    private String userType;

    private EntityManagerFactory emf;
    private EntityManager em;

    public RegistrationBean() {
        emf = Persistence.createEntityManagerFactory("triphub");
        em = emf.createEntityManager();
    }

    public String register() {
        UserDAO userDao = new UserDAO(em);
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPhoneNum(phoneNum);
        user.setPassword(password);

        em.getTransaction().begin();   // Commencer la transaction
        userDao.create(user);
        em.getTransaction().commit();  // Commit la transaction

        return "login?faces-redirect=true";
    }


	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
    
    
}
    

