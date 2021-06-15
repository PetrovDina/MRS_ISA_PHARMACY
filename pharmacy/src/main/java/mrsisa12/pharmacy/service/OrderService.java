package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.OrderItemDTO;
import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private OrderItemService orderItemService;
	
	@Autowired
	private PharmacyService pharmacyService;

	public Order findOne(Long id) {
		return orderRepository.findById(id).orElse(null);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public Page<Order> findAll(Pageable page) {
		return orderRepository.findAll(page);
	}

	public Order save(Order order) {
		return orderRepository.save(order);
	}

	@Transactional(readOnly = false)
	public void deleteOrder(Order order) {
		orderRepository.delete(order);
	}

	public Order findOneWithOrderItems(Long id) {
		return orderRepository.findOneWithOrderItems(id);
	}
	
	public Order findOneWithOffers(Long id) {
		return orderRepository.findOneWithOffers(id);
	}

	public List<Order> findAllFromPharmacy(Pharmacy pharmacy) {
		return orderRepository.findAllFromPharmacy(pharmacy);
	}
	
	public List<Order> findAllFromSupplier(Supplier supplier) {
		return orderRepository.findAllFromSupplier(supplier);
	}
	
	public List<Order> findAllFromNotSupplier(Supplier supplier) {
		return orderRepository.findAllFromNotSupplier(supplier);
	}

	public Order createOrder(OrderWithOrderItemsDTO orderDTO) {
		Order order = new Order();
		// preuzimanje datuma
		order.setDueDate(LocalDate.parse(orderDTO.getDueDate()));
		// postavljanje admina
		PharmacyAdmin pharmacyAdmin = pharmacyAdminService.findOneByUsername(orderDTO.getPharmacyAdmin().getUsername());
		order.setPharmacyAdmin(pharmacyAdmin);
		// postavljanje apoteke
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyAdmin.getPharmacy().getId());
		order.setPharmacy(pharmacy);
		// podesavanje statusa
		order.setStatus(OrderStatus.NEW);
		// nije obrisana
		order.setDeleted(false);
		// cuvamo order
		save(order);

		createAndAddOrderItems(orderDTO, order);
		
		return order;
	}

	private void createAndAddOrderItems(OrderWithOrderItemsDTO orderDTO, Order order) {
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
	}
	
}
