package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Absence;

public interface AbsenceRepository extends JpaRepository<Absence, Long>{

	public Page<Absence> findAll(Pageable pageable);
	
	@Query("select ab from Absence ab where ab.employee.id = ?1")
	public List<Absence> findAllByEmployeeId(Long id);
	
	@Query("select ab from Absence ab where ab.pharmacy.id = ?1")
	public List<Absence> findAllByPharmacyId(Long id);
	
	@Query("select ab from Absence ab where ab.employee.id = ?1 and ab.status = 'APPROVED'")
	public List<Absence> findAllAprovedAbsencesByEmployeeId(Long id);
}
