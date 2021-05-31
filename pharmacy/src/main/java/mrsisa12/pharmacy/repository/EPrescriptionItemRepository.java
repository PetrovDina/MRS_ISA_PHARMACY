package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.EPrescriptionItem;

public interface EPrescriptionItemRepository  extends JpaRepository<EPrescriptionItem, Long> {
	
	

}
