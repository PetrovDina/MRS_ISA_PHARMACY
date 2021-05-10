package mrsisa12.pharmacy.dto;

import java.time.LocalDate;

import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.enums.OfferStatus;

public class OfferDTO {
	
	private Long id;
	private String supplierUsername;
	private Long orderId;
	private Double price;
	private String deliveryDueDate;
	private OfferStatus status;
	
	public OfferDTO() { }

	public OfferDTO(Long id, String supplierUsername, Long orderId, Double price, LocalDate deliveryDueDate, OfferStatus status) {
		super();
		this.id = id;
		this.supplierUsername = supplierUsername;
		this.orderId = orderId;
		this.price = price;
		this.deliveryDueDate = (deliveryDueDate == null) ? null : deliveryDueDate.toString();
		this.status = status;
		
	}
	
	public OfferDTO(Offer offer) {
		this(offer.getId(), offer.getSupplier().getUsername(), offer.getOrder().getId(), offer.getPrice(), 
				offer.getDeliveryDueDate(), offer.getStatus());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSupplierUsername() {
		return supplierUsername;
	}

	public void setSupplierUsername(String supplierUsername) {
		this.supplierUsername = supplierUsername;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(String deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

}
