package triphub.managedBeans;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import triphub.dao.*;
import triphub.entity.user.*;
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
            
            CustomerDAO customerDao = new CustomerDAO(em);
            Customer customer = customerDao.findByUser(user);
            if (customer != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "customer");
                return "home?faces-redirect=true";
            }

            OrganizerDAO organizerDao = new OrganizerDAO(em);
            Organizer organizer = organizerDao.findByUser(user);
            if (organizer != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "organizer");
                return "home?faces-redirect=true";
            }

            ProviderDAO providerDao = new ProviderDAO(em);
            Provider provider = providerDao.findByUser(user);
            if (provider != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "provider");
                return "home?faces-redirect=true";
            }

            SuperAdminDAO superAdminDao = new SuperAdminDAO(em);
            SuperAdmin superAdmin = superAdminDao.findByUser(user);
            if (superAdmin != null) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userType", "superAdmin");
                return "home?faces-redirect=true";
            }

        }
        return "login"; 
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

