package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.PrescriptionItem;

public interface PrescriptionItemRepository extends JpaRepository<PrescriptionItem, Long>{
	
	public Page<PrescriptionItem> findAll(Pageable pageable);
	

}
