package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Pharmacist;

public interface PharmacistRepository extends JpaRepository<Pharmacist, Long> {

	@Query("select pat from Pharmacist pat where pat.username = ?1")
	public Pharmacist findByUsername(String username);
	

}
