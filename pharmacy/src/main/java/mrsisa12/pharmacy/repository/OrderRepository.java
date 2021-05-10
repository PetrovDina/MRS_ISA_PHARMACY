package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Supplier;

public interface OrderRepository extends JpaRepository<Order, Long> {

	public List<Order> findAll();

	public Page<Order> findAll(Pageable pageable);

	@Query("select ord from Order ord left join fetch ord.orderItems e where ord.id =?1")
	public Order findOneWithOrderItems(Long id);
	
	@Query("select ord from Order ord left join fetch ord.offers e where ord.id =?1")
	public Order findOneWithOffers(Long id);

	@Query("select ord from Order ord where ord.pharmacy =?1")
	public List<Order> findAllFromPharmacy(Pharmacy pharmacy);
	
	@Query("select ord from Order ord left join fetch ord.offers offer where offer.supplier =?1")
	public List<Order> findAllFromSupplier(Supplier supplier);
	
	@Query("select ord from Order ord left join fetch ord.offers offer where (offer.supplier !=?1 or offer.supplier = null) and ord.status != 'DONE' ")
	public List<Order> findAllFromNotSupplier(Supplier supplier);
}
