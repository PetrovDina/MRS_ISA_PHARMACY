package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.ComplaintEmployee;

public interface ComplaintEmployeeRepository extends JpaRepository<ComplaintEmployee, Long> {
	
	public Page<ComplaintEmployee> findAll(Pageable pageable);

}
