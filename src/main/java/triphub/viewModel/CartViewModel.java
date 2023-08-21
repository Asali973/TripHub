package triphub.viewModel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CartViewModel implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String itemName;
	private BigDecimal price;
	private int quantity;
	private String userName; // Store the user's full name as a string
	private Date dateOfOrder;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(Date dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public BigDecimal calculateItemTotalPrice() {
		return price.multiply(BigDecimal.valueOf(quantity));
	}
}
