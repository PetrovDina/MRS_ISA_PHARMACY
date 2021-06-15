package mrsisa12.pharmacy.dto.order;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.OfferDTO;
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Order;

public class OrderWithOffersDTO extends OrderWithOrderItemsDTO {

	private List<OfferDTO> offers;
	
	public OrderWithOffersDTO() {}
	
	public OrderWithOffersDTO(Order order) {
		super(order);
		fillOffers(order.getOffers());
	}
	
	private void fillOffers(List<Offer> offers) {
		if (this.offers == null)
			this.offers = new ArrayList<OfferDTO>();
		for (Offer offer : offers)
			this.offers.add(new OfferDTO(offer));
	}

	public List<OfferDTO> getOffers() {
		return offers;
	}

	public void setOffers(List<OfferDTO> offers) {
		this.offers = offers;
	}
}
