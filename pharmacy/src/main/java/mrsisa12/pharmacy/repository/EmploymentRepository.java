package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Pharmacy;

public interface EmploymentRepository extends JpaRepository<Employment, Long>{

	public List<Employment> findAll();
	
	public Page<Employment> findAll(Pageable pageable);

	@Query("select emp from Employment emp where emp.employee =?1")
	public List<Employment> findAllByEmployee(Employee empl);

	@Query("select emp from Employment emp where emp.contractType = 'PHARMACIST_CONTRACT'")
	public List<Employment> findAllPharmacistEmployments();
	
	@Query("select emp from Employment emp where emp.employee.id =?1 and emp.pharmacy.id =?2")
	public Employment findOneByEmployeeIdAndPharmacyId(Long employeeId, Long pharmacyId);
	
	@Query("select emp from Employment emp where emp.employee.id = ?1")
	public List<Employment> findAllEmploymentsOfDermatologist(Long employeeId);
	
	@Query("select emp from Employment emp where emp.contractType = 'PHARMACIST_CONTRACT' and emp.employee.username = ?1")
	public Employment findPharmacistEmploymentsByUsername(String username);

	@Query("select emp from Employment emp where emp.employee =?1 and emp.pharmacy =?2")
	public List<Employment> findAllByEmployeeAndPharmacy(Employee empl, Pharmacy pharmacy);

}
