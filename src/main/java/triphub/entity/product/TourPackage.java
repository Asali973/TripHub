package triphub.entity.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import triphub.entity.user.Organizer;
import triphub.entity.util.Picture;
import triphub.viewModel.TourPackageFormViewModel;

@Entity
public class TourPackage implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String description;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id")
	private Price price;

	@ManyToOne(cascade = CascadeType.ALL)
	private Destination destination;

	@ManyToOne(cascade = CascadeType.ALL)
	private Theme theme;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true) // image can be null
	private Picture picture;
	

	
	@ManyToOne
	@JoinColumn(name="organizer_id")
	private Organizer organizer;

	


	public TourPackage() {
	}

	public static TourPackage createTourPackageFromViewModel(TourPackageFormViewModel tourPackageVm) {
		TourPackage tourPackage = new TourPackage();
		tourPackage.setId(tourPackageVm.getId());
		tourPackage.setName(tourPackageVm.getName());
		tourPackage.setDescription(tourPackageVm.getDescription());
		
		Picture picture = new Picture();
		picture.setLink(tourPackageVm.getLink());
		tourPackage.setPicture(picture);

		Price price = new Price(tourPackageVm.getAmount(), tourPackageVm.getCurrency());
		Destination destination = new Destination(tourPackageVm.getCityName(), tourPackageVm.getState(),
				tourPackageVm.getCountry());
		Theme theme = new Theme(tourPackageVm.getThemeName());

		tourPackage.setPrice(price);
		tourPackage.setDestination(destination);
		tourPackage.setTheme(theme);

//	    List<Picture> pictures = tourPackageVm.getPictureslinks();
//	    tourPackage.setPictures(pictures);    

		return tourPackage;
	}

	public void updateTourPackageFormViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setName(tourPackageVm.getName());
		this.setId(tourPackageVm.getId());
		this.setDescription(tourPackageVm.getDescription());
		this.getPrice().updatePriceFromViewModel(tourPackageVm);
		this.getDestination().updateDestinationFromViewModel(tourPackageVm);
		this.getTheme().updateThemeFromViewModel(tourPackageVm);
		
        Picture picture = new Picture();
        picture.setLink(tourPackageVm.getLink());
        this.setPicture(picture);

	}

	public TourPackageFormViewModel initTourPackageFormViewModel() {
		TourPackageFormViewModel tourPackageVm = new TourPackageFormViewModel();

		tourPackageVm.setId(this.getId());
		tourPackageVm.setName(this.getName());
		tourPackageVm.setDescription(this.getDescription());
		this.getPrice().initPriceViewModel(tourPackageVm);
		this.getDestination().initDestinationViewModel(tourPackageVm);
		this.getTheme().initThemeViewModel(tourPackageVm);
		
        if (this.getPicture() != null) {
        	tourPackageVm.setLink(this.getPicture().getLink());
        }
		return tourPackageVm;
	}
//	tourPackageVm.setPictureslinks(this.getPictures());
	// getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public Theme getTheme() {
		return theme;
	}

	public void setTheme(Theme theme) {
		this.theme = theme;
	}



	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public void setOrganizer(Organizer organizer) {
		this.organizer = organizer;
	}

	
}
