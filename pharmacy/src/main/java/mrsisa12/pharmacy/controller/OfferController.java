package mrsisa12.pharmacy.controller;

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
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.service.OfferService;
import mrsisa12.pharmacy.service.OrderItemService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.SupplierService;
import mrsisa12.pharmacy.service.SupplierStorageItemService;

@RestController
@RequestMapping("/offer")
public class OfferController {

	@Autowired
	private OfferService offerService;

	@Autowired
	private OrderService orderService;

	@SuppressWarnings("unused")
	@Autowired
	private SupplierStorageItemService supplierStorageItemService;

	@SuppressWarnings("unused")
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private SupplierService supplierService;

	@PreAuthorize("hasRole('SUPPLIER')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<OfferDTO> saveOffer(@RequestBody OfferDTO offerDTO) {
		
		Offer offer = offerService.createOffer(offerDTO);
		
		if(offer == null)
		{
			return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('SUPPLIER')")
	@PutMapping(value = "/update", consumes = "application/json")
	public ResponseEntity<OfferDTO> updateOffer(@RequestBody OfferDTO offerDTO) {
		Offer offer = offerService.updateOffer(offerDTO);
		
		if(offer == null)
		{
			return new ResponseEntity<OfferDTO>(HttpStatus.NOT_ACCEPTABLE);
		}

		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('PHARMACY_ADMIN')")
	@PostMapping(value = "/accept", consumes = "application/json")
	public ResponseEntity<OfferDTO> acceptOffer(@RequestBody OfferDTO offerDTO) 
	{	
		Offer offer = offerService.acceptOffer(offerDTO);
		return new ResponseEntity<>(new OfferDTO(offer), HttpStatus.OK);
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
