package mrsisa12.pharmacy.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mrsisa12.pharmacy.model.enums.OfferStatus;

@Entity
@Table(name = "offers")
public class Offer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Supplier supplier;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Order order;
	
	@Column
	private Double price;
	
	@Column(name = "deliveryDueDate", nullable = false)
	private LocalDate deliveryDueDate;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OfferStatus status;
	
	public Offer() { }

	public Offer(Long id, Supplier supplier, Order order, Double price, LocalDate deliveryDueDate, OfferStatus status) {
		super();
		this.id = id;
		this.supplier = supplier;
		this.order = order;
		this.price = price;
		this.deliveryDueDate = deliveryDueDate;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public LocalDate getDeliveryDueDate() {
		return deliveryDueDate;
	}

	public void setDeliveryDueDate(LocalDate deliveryDueDate) {
		this.deliveryDueDate = deliveryDueDate;
	}

	public OfferStatus getStatus() {
		return status;
	}

	public void setStatus(OfferStatus status) {
		this.status = status;
	}

}
