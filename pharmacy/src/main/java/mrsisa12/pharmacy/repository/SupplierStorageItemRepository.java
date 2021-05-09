package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.SupplierStorageItem;

public interface SupplierStorageItemRepository extends JpaRepository<SupplierStorageItem, Long> 
{
	
	public Page<SupplierStorageItem> findAll(Pageable pageable);
	
	@Query("select s from SupplierStorageItem s join fetch s.medication where s.id =?1")
	public SupplierStorageItem findOneWithMedication(Long supplierStorageItemId);
	
}
