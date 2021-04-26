package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.TimePeriod;

public class ItemPriceDTO {

	private Long id;
	private double price;
	private boolean current;
	private TimePeriodDTO timePeriod;

	public ItemPriceDTO() {
	}

	public ItemPriceDTO(Long id, double price, boolean current, TimePeriodDTO timePeriod) {
		this();
		this.id = id;
		this.price = price;
		this.current = current;
		this.timePeriod = timePeriod;
	}

	public ItemPriceDTO(ItemPrice itemPrice) {
		this(itemPrice.getId(), itemPrice.getPrice(), itemPrice.isCurrent(), new TimePeriodDTO(itemPrice.getTimePeriod()));
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

	public TimePeriodDTO getTimePeriodDTO() {
		return timePeriod;
	}

	public void setTimePeriodDTO(TimePeriodDTO timePeriod) {
		this.timePeriod = timePeriod;
	}

}
