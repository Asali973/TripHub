package triphub.managedBeans;

import javax.annotation.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import triphub.dao.UserDAO;
import triphub.entity.user.User;

@ManagedBean
@SessionScoped
public class LoginBean {
    private String email;
    private String password;
    
    private EntityManagerFactory emf;
    private EntityManager em;

    public LoginBean() {
        emf = Persistence.createEntityManagerFactory("triphub");
        em = emf.createEntityManager();
    }

    public String login() {
        UserDAO userDao = new UserDAO(em);
        User user = userDao.findByEmail(email);

        if(user != null && user.getPassword().equals(password)) {
            return "home?faces-redirect=true";
        } else {
            return "login"; 
        }
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

}
