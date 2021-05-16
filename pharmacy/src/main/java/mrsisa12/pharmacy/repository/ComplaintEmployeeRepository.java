package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.model.SystemAdmin;

public interface ComplaintEmployeeRepository extends JpaRepository<ComplaintEmployee, Long> {
	
	public Page<ComplaintEmployee> findAll(Pageable pageable);
	
	@Query("select ce from ComplaintEmployee ce where ce.systemAdmin = null")
	public List<ComplaintEmployee> findAllByAdminNull();
	
	public ComplaintEmployee findOneById(Long id);
	
	public List<ComplaintEmployee> findAllBySystemAdmin(SystemAdmin systemAdmin); 

}
