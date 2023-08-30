package triphub.entity.product;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import triphub.viewModel.TourPackageFormViewModel;
import triphub.viewModel.UserViewModel;
/**
 * Represents a travel theme or category under which various tour packages can be grouped.
 * Each theme can have multiple associated tour packages.
 * For example, a theme might be "Adventure", "Relaxation", "Historical", etc.
 */
@Entity
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String themeName;
	@OneToMany(mappedBy = "theme")
	private List<TourPackage> tourPackages;

	 /**
     * Default constructor.
     */
	public Theme() {}
	

	/**
     * Constructs a Theme entity with the given theme name.
     * 
     * @param themeName Name of the theme.
     */
	
	public Theme(String themeName) {	
		this.themeName = themeName;
	}
	
	/**
     * Creates and returns a new Theme entity using data from the provided tour package view model.
     * 
     * @param tourPackageVm The view model representing a tour package.
     * @return A new Theme entity populated from the tour package view model.
     */
	public static Theme createThemeFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Theme theme = new Theme();
		theme.setThemeName(tourPackageVm.getThemeName());
		return theme;
	}
	
	/**
     * Updates the current Theme entity's properties using data from the provided tour package view model.
     * 
     * @param tourPackageVm The view model representing a tour package.
     */
	public void  updateThemeFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setThemeName(tourPackageVm.getThemeName());
	}
	
	/**
     * Populates the provided tour package view model with data from the current Theme entity.
     * 
     * @param tourPackageVm The view model to be populated.
     */
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
