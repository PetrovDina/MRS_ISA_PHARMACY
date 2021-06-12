package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.EmployeeRating;



public interface EmployeeRatingRepository extends JpaRepository<EmployeeRating, Long>{

	public List<EmployeeRating> findAll();

	@Query("select rat from EmployeeRating rat where rat.patient.username = ?1 and rat.employee.id = ?2")
	public EmployeeRating findOneByPatientAndEmployee(String patientUsername, Long employeeId);

	@Query("select rat from EmployeeRating rat where rat.employee.id = ?1")
	public List<EmployeeRating> findAllByEmployee(Long employeeId);

}

