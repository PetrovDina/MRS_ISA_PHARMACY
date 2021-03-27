package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public interface PharmacyStorageItemRepository extends JpaRepository<PharmacyStorageItem, Long>{
	
	public Page<PharmacyStorageItem> findAll(Pageable pageable);
	
	@Query("select s from PharmacyStorageItem s join fetch s.itemPrices e where s.id =?1")
	public PharmacyStorageItem findOneWithItemPrices(Long pharmacyStorageItemId);
}
