package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.OfferDTO;
import mrsisa12.pharmacy.dto.OfferWithOrderWithOrderItemsDTO;
import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.SupplierStorageItem;
import mrsisa12.pharmacy.model.enums.OfferStatus;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.service.OfferService;
import mrsisa12.pharmacy.service.OrderItemService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.SupplierService;
import mrsisa12.pharmacy.service.SupplierStorageItemService;

@RestController
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferService offerService;

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

	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<OfferDTO> saveOffer(@RequestBody OfferDTO offerDTO) {
		Offer offer = new Offer();
		Supplier supplier = supplierService.findOneWithStorageItems(offerDTO.getSupplierUsername());
		Order order = orderService.findOneWithOrderItems(offerDTO.getOrderId());

		for (OrderItem orderItem : order.getOrderItems()) {
			if (!medicationInList(supplier.getSupplierStorageItems(), orderItem.getMedication())) {
				return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (!hasQuantity(supplier.getSupplierStorageItems(), orderItem)) {
					return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
				}

			}
		}

		if (order.getStatus() == OrderStatus.NEW) {
			order.setStatus(OrderStatus.HAS_OFFERS);
			orderService.save(order);
		}
		offer.setSupplier(supplier);
		offer.setOrder(order);
		offer.setPrice(offerDTO.getPrice());
		offer.setDeliveryDueDate(LocalDate.parse(offerDTO.getDeliveryDueDate()));
		offer.setStatus(OfferStatus.PENDING);
		offerService.save(offer);

		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.CREATED);
	}

	@PutMapping(value = "/update", consumes = "application/json")
	public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO) {
		Offer offer = offerService.findOne(offerDTO.getId());
		Supplier supplier = supplierService.findOneWithStorageItems(offerDTO.getSupplierUsername());
		Order order = orderService.findOneWithOrderItems(offerDTO.getOrderId());

		for (OrderItem orderItem : order.getOrderItems()) {
			if (!medicationInList(supplier.getSupplierStorageItems(), orderItem.getMedication())) {
				return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
			} else {
				if (!hasQuantity(supplier.getSupplierStorageItems(), orderItem)) {
					return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
				}

			}
		}

		offer.setPrice(offerDTO.getPrice());
		offer.setDeliveryDueDate(LocalDate.parse(offerDTO.getDeliveryDueDate()));
		offerService.save(offer);

		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.OK);
	}

	@PutMapping(value = "/accept", consumes = "application/json")
	public ResponseEntity<OfferDTO> acceptOffer(@RequestBody OfferDTO offerDTO) {
		
		Order orderWithOrderItems = orderService.findOneWithOrderItems(offerDTO.getOrderId());
		Order orderWithOffers = orderService.findOneWithOffers(offerDTO.getOrderId());
		Offer offer = offerService.findOne(offerDTO.getId());
		Supplier supplier = supplierService.findOne(offerDTO.getSupplierUsername());
		// prihvatamo ponudu
		for (OrderItem orderItem : orderWithOrderItems.getOrderItems()) {
			// umanjujemo kolicinu kod supplier-a
			supplierStorageItemService.updateSupplierStorageItemQuantity(orderItem.getMedication(), offer.getSupplier(), orderItem.getQuantity());
			// povecavamo kolicinu u apoteci
			pharmacyStorageItemService.updatePharmacyStorageItemQuantity(orderItem.getMedication(), orderWithOrderItems.getPharmacy(),
					orderItem.getQuantity());
		}

		// saljemo mail o prihvatanju
		emailService.sendEmailToSupplier(supplier, offer.getPrice(), orderWithOrderItems, "ACCEPTED");
		// promjena statusa ponude koja je prihvacena
		offer.setStatus(OfferStatus.ACCEPTED);
		offerService.save(offer);
		
		// saljemo mailove o odbijanju ponuda
		for (Offer offerToReject : orderWithOffers.getOffers()) {
			if(offerToReject.getId() == offer.getId()) continue; // preskacemo onu koju smo prihvatili
			emailService.sendEmailToSupplier(offerToReject.getSupplier(), offerToReject.getPrice(), orderWithOrderItems, "REJECTED");
			// promujena statusa ponude koja je odbijena
			offerToReject.setStatus(OfferStatus.REJECTED);
			offerService.save(offerToReject);
		}

		return new ResponseEntity<>(new OfferDTO(offerService.findOne(offerDTO.getId())), HttpStatus.OK);
	}

	private boolean medicationInList(List<SupplierStorageItem> ssis, Medication med) {
		for (SupplierStorageItem supplierStorageItem : ssis) {
			if (supplierStorageItem.getMedication().getId() == med.getId())
				return true;
		}

		return false;
	}

	private boolean hasQuantity(List<SupplierStorageItem> ssis, OrderItem orderItem) {
		for (SupplierStorageItem supplierStorageItem : ssis) {
			if (supplierStorageItem.getMedication().getId() == orderItem.getMedication().getId()) {
				if (supplierStorageItem.getQuantity() >= orderItem.getQuantity())
					return true;
			}
		}

		return false;
	}

	@GetMapping(value = "/allFromSupplier/{username}")
	public ResponseEntity<List<OfferWithOrderWithOrderItemsDTO>> getAllFromSupplier(@PathVariable String username) {
		Supplier supplier = supplierService.findOne(username);

		// Dobavljam sve offer-e dobavljaca gde se za svaki offer nalazi i order
		List<Offer> offers = offerService.findAllOfSupplier(supplier);

		List<OfferWithOrderWithOrderItemsDTO> offerDTOs = new ArrayList<OfferWithOrderWithOrderItemsDTO>();
		// List<OrderWithOrderItemsDTO> orderDTOs = new
		// ArrayList<OrderWithOrderItemsDTO>();

		// Kreiram listu offerDTOs
		for (Offer offer : offers) {
			OrderWithOrderItemsDTO orderWithOrderItemsDTO = new OrderWithOrderItemsDTO(
					orderService.findOneWithOrderItems(offer.getOrder().getId()));

			OfferWithOrderWithOrderItemsDTO offerWithOrderItemsDTO = new OfferWithOrderWithOrderItemsDTO(offer,
					orderWithOrderItemsDTO);

			offerDTOs.add(offerWithOrderItemsDTO);
		}

		return new ResponseEntity<>(offerDTOs, HttpStatus.OK);
	}

}
