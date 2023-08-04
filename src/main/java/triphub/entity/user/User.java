package triphub.entity.user;

import javax.persistence.*;
import java.io.Serializable;


import triphub.entity.util.Address;
import triphub.entity.util.FinanceInfo;
import triphub.helpers.PasswordUtils;
import triphub.viewModel.UserViewModel;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String password;
    

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    private FinanceInfo finance;
    
    public static User createUserFromViewModel(UserViewModel form) {
        User user = new User();
        user.setId(form.getUserId());
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPhoneNum(form.getPhoneNum());
        user.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
        return user;
    }
    
    public void updateUserFromViewModel(UserViewModel form) {
        this.setFirstName(form.getFirstName());
        this.setLastName(form.getLastName());
        this.setEmail(form.getEmail());
        this.setPhoneNum(form.getPhoneNum());
        this.setPassword(PasswordUtils.getInstance().hashPassword(form.getPassword()));
    }
    
    public void initUserViewModel(UserViewModel userViewModel) {
        userViewModel.setUserId(this.getId());
        userViewModel.setFirstName(this.getFirstName());
        userViewModel.setLastName(this.getLastName());
        userViewModel.setEmail(this.getEmail());
        userViewModel.setPhoneNum(this.getPhoneNum());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public FinanceInfo getFinance() {
		return finance;
	}

	public void setFinance(FinanceInfo finance) {
		this.finance = finance;
	}
	
    
    
}

