package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>{
	
	public Page<Pharmacy> findAll(Pageable pageable);
	
	public List<Pharmacy> findAllByName(String name);
	
	public List<Pharmacy> findByNameAllIgnoringCase(String name);
	
	@Query("select s from Pharmacy s join fetch s.pharmacyStorageItems e where s.id =?1")
	public Pharmacy findOneWithStorageItems(Long pharmacyId);
	
}
