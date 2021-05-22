package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Promotion;

public interface PromotionRepository extends JpaRepository<Promotion, Long>{

	public List<Promotion> findAll();
	
	public Page<Promotion> findAll(Pageable pageable);

	@Query("select promotion from Promotion promotion join fetch promotion.pharmacyStorageItems e")
	public List<Promotion> findAllWithPromotionItems();

	@Query("select promotion from Promotion promotion join fetch promotion.pharmacyStorageItems e where promotion.id=?1")
	public Promotion findOneWithPromotionItems(Long id);

}