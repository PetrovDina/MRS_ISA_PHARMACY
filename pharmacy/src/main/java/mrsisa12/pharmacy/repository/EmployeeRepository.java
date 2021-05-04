package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public Page<Employee> findAll(Pageable pageable);

	@Query("select emp from Employee emp left join fetch emp.appointments appos where emp.id =?1")
	public Employee findOneWithAllAppointments(Long id);

}
