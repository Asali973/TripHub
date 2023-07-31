package triphub.entity.product;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Theme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String themeName;
	@OneToMany(mappedBy = "theme")
	private List<TourPackage> tourPackages;

	public Theme() {}
		
	public Theme(String name) {
	
		this.themeName = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return themeName;
	}

	public void setName(String name) {
		this.themeName = name;
	}

	public List<TourPackage> getTourPackages() {
		return tourPackages;
	}

	public void setTourPackages(List<TourPackage> tourPackages) {
		this.tourPackages = tourPackages;
	}

}
