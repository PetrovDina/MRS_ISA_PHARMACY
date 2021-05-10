package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
	
	@Query("select s from Supplier s left join fetch s.supplierStorageItems e where s.id =?1")
	public Supplier findOneWithStorageItems(Long supplierId);
	
	@Query("select s from Supplier s left join fetch s.supplierStorageItems e where s.username =?1")
	public Supplier findOneWithStorageItems(String supplierUsername);
	
	@Query("select s from Supplier s where s.username =?1")
	public Supplier findOne(String username);
}
