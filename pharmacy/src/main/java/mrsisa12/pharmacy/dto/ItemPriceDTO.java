package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ItemPrice;

public class ItemPriceDTO {

	 private Long id;
	 private double price;
	 private boolean current;
	 
	 public ItemPriceDTO() {
		 
	 }
	 
	 public ItemPriceDTO(Long id, double price, boolean current) {
		this();
		this.id = id;
		this.price = price;
		this.current = current;
	}

	public ItemPriceDTO(ItemPrice itemPrice) {
		 this(itemPrice.getId(), itemPrice.getPrice(), itemPrice.isCurrent());
	 }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}
}
