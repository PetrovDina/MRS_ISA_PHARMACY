package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.PharmacyAdmin;

public interface PharmacyAdminRepository extends JpaRepository<PharmacyAdmin, Long>
{
	
	public Page<PharmacyAdmin> findAll(Pageable pageable);

	public PharmacyAdmin findByUsername(String username);
	
}
