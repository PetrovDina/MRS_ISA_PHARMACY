package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.ItemPrice;


public interface ItemPriceRepository extends JpaRepository<ItemPrice, Long>{

	public Page<ItemPrice> findAll(Pageable pageable);
	
	@Query("select price from ItemPrice price where price.current = ?1")
	public List<ItemPrice> findAllCurrent(Boolean current);
	
}
