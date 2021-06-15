package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.repository.OrderItemRepository;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemRepository orderItemRepository;

	public OrderItem findOne(Long id) {
		return orderItemRepository.findById(id).orElse(null);
	}

	public List<OrderItem> findAll() {
		return orderItemRepository.findAll();
	}

	public Page<OrderItem> findAll(Pageable page) {
		return orderItemRepository.findAll(page);
	}

	public OrderItem save(OrderItem orderItem) {
		return orderItemRepository.save(orderItem);
	}

	@Transactional(readOnly = false)
	public void deleteOrder(OrderItem orderItem) {
		orderItemRepository.delete(orderItem);
	}
}
