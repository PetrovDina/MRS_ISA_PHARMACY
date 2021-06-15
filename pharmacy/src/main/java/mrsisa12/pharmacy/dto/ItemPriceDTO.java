package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ItemPrice;

public class ItemPriceDTO {

	private Long id;
	private double price;
	private boolean current;
	private boolean promotion;
	private TimePeriodDTO timePeriod;

	public ItemPriceDTO() {
	}

	public ItemPriceDTO(Long id, double price, boolean current, boolean promotion, TimePeriodDTO timePeriod) {
		this();
		this.id = id;
		this.price = price;
		this.current = current;
		this.promotion = promotion;
		this.timePeriod = timePeriod;
	}

	public ItemPriceDTO(ItemPrice itemPrice) {
		this(itemPrice.getId(), itemPrice.getPrice(), itemPrice.isCurrent(), itemPrice.isPromotion(), new TimePeriodDTO(itemPrice.getTimePeriod()));
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

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	public TimePeriodDTO getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriodDTO timePeriod) {
		this.timePeriod = timePeriod;
	}

}
