package mrsisa12.pharmacy.dto;

import java.time.LocalDate;

import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.enums.OfferStatus;

public class OfferWithOrderWithOrderItemsDTO extends OfferDTO {
	
	private OrderWithOrderItemsDTO order;

	public OfferWithOrderWithOrderItemsDTO() { }
	
	public OrderWithOrderItemsDTO getOrderWithOrderItemsDTO() {
		return order;
	}

	public void setOrder(OrderWithOrderItemsDTO order) {
		this.order = order;
	}

	public OfferWithOrderWithOrderItemsDTO(Long id, String supplierUsername, Long orderId, Double price,
			LocalDate deliveryDueDate, OfferStatus status) {
		super(id, supplierUsername, orderId, price, deliveryDueDate, status);

	}

	public OfferWithOrderWithOrderItemsDTO(Offer offer, OrderWithOrderItemsDTO order) {
		super(offer);
		this.order = order;
	}

	public OrderWithOrderItemsDTO getOrder() {
		return order;
	}
	
	
	
}
