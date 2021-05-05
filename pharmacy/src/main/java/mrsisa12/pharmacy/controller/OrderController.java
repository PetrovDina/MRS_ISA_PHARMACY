package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.OrderItemService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.PharmacyAdminService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PharmacyAdminService pharmacyAdminService;

	@Autowired
	private OrderItemService orderItemService;

	@Autowired
	private MedicationService medicationService;

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
		Order order = new Order();
		// preuzimanje datuma
		order.setDueDate(orderDTO.getDueDate());
		// postavljanje admina
		PharmacyAdmin pharmacyAdmin = pharmacyAdminService.findOne(orderDTO.getPharmacyAdmin().getId());
		order.setPharmacyAdmin(pharmacyAdmin);
		// nije obrisana
		order.setDeleted(false);
		// cuvamo order
		orderService.save(order);

		// kreiranje svih odabranih orderItem-a
		for (OrderItemDTO orderItemDto : orderDTO.getOrderItems()) {
			OrderItem orderItem = new OrderItem();
			// postavljanje lijeka
			Medication medication = medicationService.findOne(orderItemDto.getMedication().getId());
			orderItem.setMedication(medication);
			// postavljanje kolicine
			orderItem.setQuantity(orderItemDto.getQuantity());
			// postavljanje porudzbine za koju je vezan
			orderItem.setOrder(order);
			// nije obrisan
			orderItem.setDeleted(false);
			orderItemService.save(orderItem);
			order.addOrderItem(orderItem);
		}
		
		return new ResponseEntity<>(new OrderWithOrderItemsDTO(order), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) {

		// an order must exist
		Order order = orderService.findOne(orderDTO.getId());

		if (order == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// PRETPOTAVLJAM DA CE SE MOCI UREDJIVATI SAMO DATUM

		order = orderService.save(order);
		return new ResponseEntity<>(new OrderDTO(order), HttpStatus.OK);
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
