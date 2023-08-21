package triphub.entity.product;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import triphub.viewModel.SubServicesViewModel;
import triphub.viewModel.TourPackageFormViewModel;

@Entity
public class Price {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private BigDecimal amount;
	private String currency;
	
	@OneToOne(mappedBy = "price")
	private TourPackage tourPackage;
			
	public Price() {}
	
	public Price(BigDecimal amount, String currency) {
		super();
		this.amount = amount;
		this.currency = currency;
	}
	
	public static Price createPriceFromViewModel(TourPackageFormViewModel tourPackageVm) {
		Price price= new Price();
		price.setAmount(tourPackageVm.getAmount());
		price.setCurrency(tourPackageVm.getCurrency());
		return price;
	}
	
	public void  updatePriceFromViewModel(TourPackageFormViewModel tourPackageVm) {
		this.setAmount(tourPackageVm.getAmount());
		this.setCurrency(tourPackageVm.getCurrency());
	}
	public void  initPriceViewModel(TourPackageFormViewModel tourPackageVm) {
		tourPackageVm.setAmount(this.getAmount());
		tourPackageVm.setCurrency(this.getCurrency());
	}

	public static Price createPriceFromViewModel(SubServicesViewModel serviceVm) {
		Price price= new Price();
		price.setAmount(serviceVm.getPrice().getAmount());
		price.setCurrency(serviceVm.getPrice().getCurrency());
		return price;
	}
	
	public void  updatePriceFromViewModel(SubServicesViewModel servicevm) {
		this.setAmount(servicevm.getPrice().getAmount());
		this.setCurrency(servicevm.getPrice().getCurrency());
	}
	
	public void  initPriceViewModel(SubServicesViewModel servicevm) {
		servicevm.getPrice().setAmount(this.getAmount());
		servicevm.getPrice().setCurrency(this.getCurrency());
	}
	
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
