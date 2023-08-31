package triphub.entity.util;

import javax.persistence.*;

import triphub.viewModel.UserViewModel;

/**
 * Represents administrative details for an entity in the system. Contains
 * information such as SIRET number, contact phone, sector, and administrative
 * email.
 */
@Entity
public class Administration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String siret;
	private String phone;
	private String sector;
	private String email;

	/**
	 * Creates and returns an Administration object populated from the provided
	 * UserViewModel.
	 * 
	 * @param form The UserViewModel containing administrative details.
	 * @return A new Administration object.
	 */
	public static Administration createAdministrationFromViewModel(UserViewModel form) {
		Administration administration = new Administration();
		administration.setSiret(form.getSiret());
		administration.setPhone(form.getPhone());
		administration.setSector(form.getSector());
		administration.setEmail(form.getAdminEmail());
		return administration;
	}

	/**
	 * Updates the properties of the Administration based on the provided
	 * UserViewModel.
	 * 
	 * @param form The UserViewModel containing updated administrative details.
	 */
	public void updateAdministrationFromViewModel(UserViewModel form) {
		this.setSiret(form.getSiret());
		this.setPhone(form.getPhone());
		this.setSector(form.getSector());
		this.setEmail(form.getAdminEmail());
	}

	/**
	 * Initializes the provided UserViewModel with the properties of this
	 * Administration object.
	 * 
	 * @param userViewModel The UserViewModel to be populated with administrative
	 *                      details.
	 */
	public void initAdministrationViewModel(UserViewModel userViewModel) {
		userViewModel.setSiret(this.getSiret());
		userViewModel.setPhone(this.getPhone());
		userViewModel.setSector(this.getSector());
		userViewModel.setAdminEmail(this.getEmail());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSiret() {
		return siret;
	}

	public void setSiret(String siret) {
		this.siret = siret;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
