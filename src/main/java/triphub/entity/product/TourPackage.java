package triphub.entity.product;

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

import triphub.entity.util.Picture;

@Entity
public class TourPackage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "price_id")
	private Price price;

	@ManyToOne
	private Destination destination;

//    @OneToOne(cascade = CascadeType.ALL)
//    private Product item;

	@ManyToOne
	private Theme theme;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true) // image can be null
	private List<Picture> pictures;

	public TourPackage() {
	}

	public TourPackage(String name, Price price, Destination destination, Theme theme, List<Picture> pictures) {
		super();
		this.name = name;
		this.price = price;
		this.destination = destination;
		this.theme = theme;
		this.pictures = pictures;
	}

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

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

}
