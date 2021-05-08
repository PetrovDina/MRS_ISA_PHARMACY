package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

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

	public List<Order> findAllFromPharmacy(Pharmacy pharmacy) {
		return orderRepository.findAllFromPharmacy(pharmacy);
	}
}
