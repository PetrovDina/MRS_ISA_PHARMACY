package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.OrderItemDTO;
import mrsisa12.pharmacy.dto.order.OrderDTO;
import mrsisa12.pharmacy.dto.order.OrderWithOffersDTO;
import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.OrderItemService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.PharmacyAdminService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.SupplierService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private SupplierService supplierService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<OrderDTO>> getAllOrders() {

		List<Order> orders = orderService.findAll();

		// convert orders to DTOs
		List<OrderDTO> ordersDTO = new ArrayList<>();
		for (Order order : orders) {
			ordersDTO.add(new OrderDTO(order));
		}

		return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SUPPLIER')")
	@GetMapping(value = "/allNotDone/{username}")
	public ResponseEntity<List<OrderWithOrderItemsDTO>> getAllNotDoneOrdersForSupplier(@PathVariable String username) {
		Supplier supplier = supplierService.findOne(username);
		
		// Vraca sve nraudzbenice koje nisu DONE i koje nisu od prosledjenog dobavljaca
		List<Order> orders = orderService.findAllFromNotSupplier(supplier);

		// convert orders to DTOs
		List<OrderWithOrderItemsDTO> ordersDTO = new ArrayList<>();
		for (Order order : orders) 
		{
			if(order.getStatus() != OrderStatus.DONE)
			{
				System.err.println("Dodajem novi order sa id: " + order.getId());
				ordersDTO.add(new OrderWithOrderItemsDTO(orderService.findOneWithOrderItems(order.getId())));
			}
				
		}

		return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
	}

	
	@GetMapping(value = "/allFrom/{id}/withOffers")
	public ResponseEntity<List<OrderWithOffersDTO>> getAllOrdersFromPharmacy(@PathVariable Long id) {
		// Orders with offers and orderItems
		Pharmacy pharmacy = pharmacyService.findOne(id);
		List<Order> orders = orderService.findAllFromPharmacy(pharmacy);

		// convert orders to DTOs
		List<OrderWithOffersDTO> ordersDTO = new ArrayList<>();
		for (Order order : orders) {
			Order orderTemp = orderService.findOneWithOrderItems(order.getId());
			orderTemp.setOffers(orderService.findOneWithOffers(order.getId()).getOffers());
			ordersDTO.add(new OrderWithOffersDTO(orderTemp));
		}

		return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<OrderDTO>> getOrdersPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<Order> orders = orderService.findAll(page);

		// convert orders to DTOs
		List<OrderDTO> ordersDTO = new ArrayList<>();
		for (Order e : orders) {
			ordersDTO.add(new OrderDTO(e));
		}

		return new ResponseEntity<>(ordersDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {

		Order order = orderService.findOneWithOrderItems(id);

		if (order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new OrderWithOrderItemsDTO(order), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<OrderDTO> saveOrder(@RequestBody OrderWithOrderItemsDTO orderDTO) {
		Order order = orderService.createOrder(orderDTO);
		return new ResponseEntity<>(new OrderWithOrderItemsDTO(order), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<OrderWithOrderItemsDTO> updateOrder(@RequestBody OrderWithOrderItemsDTO orderDTO) {

		// an order must exist
		Order order = orderService.findOne(orderDTO.getId());

		if (order == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// promjena datuma
		order.setDueDate(LocalDate.parse(orderDTO.getDueDate()));
		// cuvanje promjene datuma
		order = orderService.save(order);
		
		// promjena kolicine narucenih lijekova
		for (OrderItemDTO orderItemDTO : orderDTO.getOrderItems()) {
			// pronalazak orderItem-a (lijeka sa kolicinom)
			OrderItem orderItem = orderItemService.findOne(orderItemDTO.getId());
			if(orderItemDTO.getQuantity() != orderItem.getQuantity()) {
				// postavljanje nove kolicine
				orderItem.setQuantity(orderItemDTO.getQuantity());
				// sacuvaj promjenu
				orderItemService.save(orderItem);
			}
		}
		order = orderService.findOneWithOrderItems(orderDTO.getId());
		return new ResponseEntity<>(new OrderWithOrderItemsDTO(order), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {

		Order order = orderService.findOne(id);

		if (order != null) {
			orderService.deleteOrder(order);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
