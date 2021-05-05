package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findAll();

	public Page<Order> findAll(Pageable pageable);

	@Query("select ord from Order ord left join fetch ord.orderItems e where ord.id =?1")
	public Order findOneWithOrderItems(Long id);

}
