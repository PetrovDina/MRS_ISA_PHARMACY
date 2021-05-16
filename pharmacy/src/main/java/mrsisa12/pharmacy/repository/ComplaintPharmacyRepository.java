package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.SystemAdmin;

public interface ComplaintPharmacyRepository extends JpaRepository<ComplaintPharmacy, Long>{
	
	public Page<ComplaintPharmacy> findAll(Pageable pageable);
	
	@Query("select cp from ComplaintPharmacy cp where cp.systemAdmin = null")
	public List<ComplaintPharmacy> findAllByAdminNull();
	
	public ComplaintPharmacy findOneById(Long id);
	
	public List<ComplaintPharmacy> findAllBySystemAdmin(SystemAdmin systemAdmin);

	public List<ComplaintPharmacy> findAllByPatient(Patient patient); 
}
