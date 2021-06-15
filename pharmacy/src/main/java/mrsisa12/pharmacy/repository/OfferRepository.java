package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Supplier;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	public List<Offer> findAll();
	
	public Page<Offer> findAll(Pageable pageable);
	
	@Query("select offer from Offer offer where offer.supplier =?1")
	public List<Offer> findAllOfSupplier(Supplier supplier);
}
