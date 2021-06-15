package mrsisa12.pharmacy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrsisa12.pharmacy.model.LoyaltyProgram;

public interface LoyaltyProgramRepository extends JpaRepository<LoyaltyProgram, Long> {
	
	public LoyaltyProgram findOneById(Long id);

}
