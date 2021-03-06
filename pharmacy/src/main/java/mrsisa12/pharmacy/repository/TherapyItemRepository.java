package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import mrsisa12.pharmacy.model.TherapyItem;

public interface TherapyItemRepository extends JpaRepository<TherapyItem, Long>{
	
	public Page<TherapyItem> findAll(Pageable pageable);
	

}
