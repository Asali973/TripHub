package triphub.managedBeans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import triphub.dao.UserDAO;
import triphub.entity.user.User;
import javax.persistence.*;

import org.mindrot.jbcrypt.BCrypt;

@ManagedBean
@SessionScoped
public class LoginBean {
    private String email;
    private String password;
    private User user;
    
    private EntityManagerFactory emf;
    private EntityManager em;

    public LoginBean() {
        emf = Persistence.createEntityManagerFactory("triphub");
        em = emf.createEntityManager();
    }

    public String login() {
        UserDAO userDao = new UserDAO(em);
        User user = userDao.findByEmail(email);

        if(user != null && BCrypt.checkpw(password, user.getPassword())) {
            this.user = user;
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

