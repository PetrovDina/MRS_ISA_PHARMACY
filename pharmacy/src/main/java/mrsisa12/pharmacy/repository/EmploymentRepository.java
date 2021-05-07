package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;

public interface EmploymentRepository extends JpaRepository<Employment, Long>{

	public List<Employment> findAll();
	
	public Page<Employment> findAll(Pageable pageable);

	@Query("select emp from Employment emp where emp.employee =?1")
	public List<Employment> findAllByEmployee(Employee empl);

	@Query("select emp from Employment emp where emp.contractType = 'PHARMACIST_CONTRACT'")
	public List<Employment> findAllPharmacistEmployments();

}
