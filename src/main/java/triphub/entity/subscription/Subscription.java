package triphub.entity.subscription;

import javax.persistence.*;

import triphub.entity.product.Price;

import java.util.List;

/**
 * Represents a user's subscription that includes various features and customizations.
 * <p>
 * Depending on the type of subscription, users can be allowed different types of customizations such as custom theme,
 * custom logo, layout change, or dark theme.
 * </p>
 */

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

	/**
     * Checks if the subscription allows users to have a custom theme.
     * 
     * @return {@code true} if the subscription type is PREMIUM, otherwise {@code false}
     */
	public boolean allowsCustomTheme() {
		switch (this.type) {
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}

	 /**
     * Checks if the subscription allows users to have a custom logo.
     * 
     * @return {@code true} if the subscription type is DELUXE or PREMIUM, otherwise {@code false}
     */
	public boolean allowsCustomLogo() {
		switch (this.type) {
		case DELUXE:
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}
	
	/**
     * Checks if the subscription allows users to change the layout.
     * 
     * @return {@code true} if the subscription type is DELUXE or PREMIUM, otherwise {@code false}
     */
	public boolean allowsLayoutChange() {
		switch (this.type) {
		case DELUXE:
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}
	
	/**
     * Checks if the subscription allows users to use a dark theme.
     * 
     * @return {@code true} if the subscription type is PREMIUM, otherwise {@code false}
     */
	public boolean allowsDarkTheme() {
		switch (this.type) {
		case PREMIUM:
			return true;
		default:
			return false;
		}
	}
	
	 /**
     * Converts the subscription into its associated customization.
     * 
     * @return {@link Customization} object representing the customizations of this subscription
     */
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
