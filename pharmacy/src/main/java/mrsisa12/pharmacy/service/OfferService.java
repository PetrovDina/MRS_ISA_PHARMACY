package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.OfferDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.enums.OfferStatus;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.repository.OfferRepository;

@Service
public class OfferService {
	
	@Autowired
	private OfferRepository offerRepository;


	@Autowired
	private OrderService orderService;

	@Autowired
	private SupplierStorageItemService supplierStorageItemService;

	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;

	@SuppressWarnings("unused")
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private EmailService emailService;
	
	public Offer findOne(Long id) {
		return offerRepository.findById(id).orElse(null);
	}

	public List<Offer> findAll() {
		return offerRepository.findAll();
	}
	
	public Page<Offer> findAll(Pageable page) {
		return offerRepository.findAll(page);
	}
	
	public Offer save(Offer offer) {
		return offerRepository.save(offer);
	}
	
	public List<Offer> findAllOfSupplier(Supplier supplier)
	{
		return offerRepository.findAllOfSupplier(supplier);
	}

	
	@Transactional(propagation = Propagation.REQUIRED)
	public Offer acceptOffer(OfferDTO offerDTO) {
		
		Order orderWithOrderItems = orderService.findOneWithOrderItems(offerDTO.getOrderId());
		Order orderWithOffers = orderService.findOneWithOffers(offerDTO.getOrderId());
		Offer offer = this.findOne(offerDTO.getId());
		Supplier supplier = supplierService.findOne(offerDTO.getSupplierUsername());
		// prihvatamo ponudu
		for (OrderItem orderItem : orderWithOrderItems.getOrderItems()) {
			// umanjujemo kolicinu kod supplier-a
			supplierStorageItemService.updateSupplierStorageItemQuantity(orderItem.getMedication(), offer.getSupplier(), orderItem.getQuantity());
			// povecavamo kolicinu u apoteci
			//pessimistic write
			pharmacyStorageItemService.updatePharmacyStorageItemQuantity(orderItem.getMedication(), orderWithOrderItems.getPharmacy(),
					orderItem.getQuantity());
			pharmacyStorageItemService.updatePharmacyStorageItemCounter(orderItem.getMedication(), orderWithOrderItems.getPharmacy());
		}
		orderWithOffers.setStatus(OrderStatus.DONE);
		orderService.save(orderWithOffers);
		// saljemo mail o prihvatanju
		emailService.sendEmailToSupplier(supplier, offer.getPrice(), orderWithOrderItems, "ACCEPTED");
		// promjena statusa ponude koja je prihvacena
		offer.setStatus(OfferStatus.ACCEPTED);
		this.save(offer);
		
		// saljemo mailove o odbijanju ponuda
		for (Offer offerToReject : orderWithOffers.getOffers()) {
			if(offerToReject.getId() == offer.getId()) continue; // preskacemo onu koju smo prihvatili
			emailService.sendEmailToSupplier(offerToReject.getSupplier(), offerToReject.getPrice(), orderWithOrderItems, "REJECTED");
			// promujena statusa ponude koja je odbijena
			offerToReject.setStatus(OfferStatus.REJECTED);
			this.save(offerToReject);
		}
		
		return offer;
	}

}
