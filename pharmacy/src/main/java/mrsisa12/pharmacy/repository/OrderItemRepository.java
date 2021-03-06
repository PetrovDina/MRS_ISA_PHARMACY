package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

	public List<OrderItem> findAll();

	public Page<OrderItem> findAll(Pageable pageable);

}