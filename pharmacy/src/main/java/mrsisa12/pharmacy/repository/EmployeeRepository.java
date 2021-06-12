package mrsisa12.pharmacy.repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import mrsisa12.pharmacy.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	public Page<Employee> findAll(Pageable pageable);

	@Query("select emp from Employee emp left join fetch emp.appointments appos where emp.id =?1")
	public Employee findOneWithAllAppointments(Long id);
	
	public Employee findByUsername(String username);
	
	@Query("select emp from Employee emp left join fetch emp.appointments appos where emp.username =?1")
	public Employee findOneByUsernameWithAppointments(String username);

	@Lock(LockModeType.PESSIMISTIC_READ)
	@Query("select emp from Employee emp where emp.id =?1")
	@QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
	public Employee findOneEmployee(Long id);

}
