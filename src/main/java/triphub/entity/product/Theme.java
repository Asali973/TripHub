package triphub.entity.product;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import triphub.viewModel.TourPackageFormViewModel;
import triphub.viewModel.UserViewModel;

@Entity
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String themeName;
	@OneToMany(mappedBy = "theme")
	private List<TourPackage> tourPackages;

	public Theme() {}
	

	public Theme(String themeName) {	
		this.themeName = themeName;
	}
	public static Theme createThemeFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Theme theme = new Theme();
		theme.setThemeName(tourPackageVm.getThemeName());
		return theme;
	}
	public void  updateThemeFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setThemeName(tourPackageVm.getThemeName());
	}
	
	public void initThemeViewModel(TourPackageFormViewModel tourPackageVm) {
		tourPackageVm.setThemeName(this.getThemeName());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}
		


}
