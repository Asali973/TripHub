package triphub.entity.subscription;

import javax.persistence.*;

import triphub.entity.product.Price;

import java.util.List;

@Entity
public class Subscription {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Price price;

	private int duration;

	@Enumerated(EnumType.STRING)
	private SubscriptionType type;

	@OneToOne(cascade = CascadeType.ALL)
	private Customization customization;

	public boolean allowsCustomTheme() {
		switch (this.type) {
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}

	public boolean allowsCustomLogo() {
		switch (this.type) {
		case DELUXE:
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}

	public boolean allowsLayoutChange() {
		switch (this.type) {
		case DELUXE:
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}

	public boolean allowsDarkTheme() {
		switch (this.type) {
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}

	public Customization toCustomization() {
		if (this.customization == null) {
			this.customization = new Customization();
		}
		return this.customization;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public SubscriptionType getType() {
		return type;
	}

	public void setType(SubscriptionType type) {
		this.type = type;
	}

	public Customization getCustomization() {
		return customization;
	}

	public void setCustomization(Customization customization) {
		this.customization = customization;
	}

}
