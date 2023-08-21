package triphub.entity.product.service;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import triphub.entity.product.Price;
import triphub.entity.product.service.accommodation.Accommodation;
import triphub.entity.util.Date;

import triphub.viewModel.SubServicesViewModel;


@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	@OneToOne(mappedBy = "service")
    private Accommodation accommodation;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Price price;

    private boolean availability;
    @OneToOne
    @JoinColumn(name = "fromDate_id")
    private Date availableFrom;
    @OneToOne
    @JoinColumn(name = "tillDate_id")
    private Date availableTill;
  
    public static Service createServiceFromViewModel(SubServicesViewModel form) {
       Service service = new Service();
        service.setId(form.getId());
        service.setPrice(form.getPrice());
        service.setAvailability(form.isAvailability());
        service.setAvailableFrom(form.getAvailableFrom());
        service.setAvailableTill(form.getAvailableTill());
        return service;
    }

    public void updateServiceFromViewModel(SubServicesViewModel form) {
         this.setPrice(form.getPrice());
         this.setAvailability(form.isAvailability());
         this.setAvailableFrom(form.getAvailableFrom());
         this.setAvailableTill(form.getAvailableTill());
    }
    
    public void initServiceViewModel(SubServicesViewModel form) {
        form.setId(this.getId());
        form.setPrice(this.getPrice());
        form.setAvailability(this.isAvailability());
        form.setAvailableFrom(this.getAvailableFrom());
        form.setAvailableTill(this.getAvailableTill());
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
	public boolean isAvailability() {
		return availability;
	}
	public void setAvailability(boolean availability) {
		this.availability = availability;
	}
	public Date getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(Date availableFrom) {
		this.availableFrom = availableFrom;
	}
	public Date getAvailableTill() {
		return availableTill;
	}
	public void setAvailableTill(Date availableTill) {
		this.availableTill = availableTill;
	}

	
}
