package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasRole('SUPPLIER')")
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
		
		for (OrderItem orderItem : order.getOrderItems()) 
		{
            supplierStorageItemService.updateSupplierStorageItemReservedQuantity(orderItem.getMedication(), supplier, orderItem.getQuantity());
		}
		
		if(order.getStatus() == OrderStatus.NEW)
		{
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

	@PreAuthorize("hasRole('SUPPLIER')")
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

	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@PostMapping(value = "/accept", consumes = "application/json")
	public ResponseEntity<OfferDTO> acceptOffer(@RequestBody OfferDTO offerDTO) {
		
		
		Offer offer = offerService.acceptOffer(offerDTO);
		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.OK);
	}

	private boolean medicationInList(List<SupplierStorageItem> ssis, Medication med) {
		for (SupplierStorageItem supplierStorageItem : ssis) {
			if (supplierStorageItem.getMedication().getId() == med.getId())
				return true;
		}

		return false;
	}
	
	private boolean hasQuantity(List<SupplierStorageItem> ssis, OrderItem orderItem)
	{
		for (SupplierStorageItem supplierStorageItem : ssis) 
		{
			if(supplierStorageItem.getMedication().getId() == orderItem.getMedication().getId())
			{
				if(supplierStorageItem.getQuantity() - supplierStorageItem.getReservedQuantity() >= orderItem.getQuantity())
					return true;
			}
		}

		return false;
	}

	@PreAuthorize("hasRole('SUPPLIER')")
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
