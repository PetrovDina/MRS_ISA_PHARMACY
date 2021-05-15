package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.ComplaintPharmacy;

public interface ComplaintPharmacyRepository extends JpaRepository<ComplaintPharmacy, Long>{
	
	public Page<ComplaintPharmacy> findAll(Pageable pageable);

}
