package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	public List<Rating> findAll();
	

}
