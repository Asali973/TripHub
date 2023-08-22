package triphub.entity.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import triphub.entity.product.service.Service;
import triphub.entity.user.User;
import triphub.viewModel.CartViewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class CartItem implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private TourPackage tourPackage;

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;

	private int quantity;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateOfOrder;
	
	@Transient
    private int newQuantity = quantity ;
	
	private BigDecimal totalPrice;
	
	
	
	 
	
	 public void updateFromCartItemViewModel(CartViewModel viewModel) {
	        this.setQuantity(viewModel.getQuantity());
	        
	      
	    }
	public void setQuantity(int quantity) {		
		this.quantity = quantity;
		
	}
		
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TourPackage getTourPackage() {
		return tourPackage;
	}

	public void setTourPackage(TourPackage tourPackage) {
		this.tourPackage = tourPackage;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public int getNewQuantity() {
		return newQuantity;
	}

	public void setNewQuantity(int newQuantity) {
		this.newQuantity = newQuantity;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartItem [id=").append(id).append(", tourPackage=").append(tourPackage).append(", service=")
				.append(service).append(", quantity=").append(quantity).append(", user=").append(user)
				.append(", dateOfOrder=").append(dateOfOrder).append(", newQuantity=").append(newQuantity)
				.append(", totalPrice=").append(totalPrice).append("]");
		return builder.toString();
	}

}
