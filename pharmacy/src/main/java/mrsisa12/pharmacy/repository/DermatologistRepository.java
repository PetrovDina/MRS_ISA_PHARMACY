package mrsisa12.pharmacy.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Dermatologist;

public interface DermatologistRepository extends JpaRepository<Dermatologist, Long>{

	public Page<Dermatologist> findAll(Pageable pageable);

	public Dermatologist findOneByUsername(String username);

		
}
