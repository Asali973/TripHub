package triphub.entity.user;

import javax.persistence.*;

import triphub.entity.util.Picture;
import triphub.viewModel.UserViewModel;
/**
 * Represents a super administrator entity in the system.
 */
@Entity
public class SuperAdmin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;
    
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Picture picture;
	
	/**
	 * Updates the SuperAdmin and its associated entities 
	 * (User, Address, FinanceInfo, and Picture) from a UserViewModel.
	 * 
	 * @param form The UserViewModel containing the updated data.
	 */
    public void updateSuperAdminFromViewModel(UserViewModel form) {
    	this.setId(form.getSuperAdminId());
        this.getUser().updateUserFromViewModel(form);
        this.getUser().getAddress().updateAddressFromViewModel(form);
        this.getUser().getFinance().updateFinanceInfoFromViewModel(form);
        this.getPicture().updatePictureFromViewModel(form);
    }

    /**
     * Initializes a UserViewModel from the data of the SuperAdmin and its 
     * associated entities (User, Address, FinanceInfo, and Picture).
     * 
     * @return UserViewModel The initialized view model with data from SuperAdmin and its associations.
     */
    public UserViewModel initSuperAdminViewModel() {
        UserViewModel userViewModel = new UserViewModel();
        this.getUser().initUserViewModel(userViewModel);
        this.getUser().getAddress().initAddressViewModel(userViewModel);
        this.getUser().getFinance().initFinanceInfoViewModel(userViewModel);
        this.getPicture().initPictureViewModel(userViewModel);
        userViewModel.setSuperAdminId(this.getId());

        return userViewModel;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}
    
    
}
