package mrsisa12.pharmacy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.PharmacyStorageItem;

public interface PharmacyStorageItemRepository extends JpaRepository<PharmacyStorageItem, Long> {

	public Page<PharmacyStorageItem> findAll(Pageable pageable);

	@Query(value = "UPDATE PharmacyStorageItem s SET s.deleted = false WHERE s.id = ?1")
	@Modifying
	public void restoreById(Long id);

	@Query("select s from PharmacyStorageItem s join fetch s.itemPrices e where s.id =?1")
	public PharmacyStorageItem findOneWithItemPrices(Long pharmacyStorageItemId);

	@Query("select s from PharmacyStorageItem s join fetch s.medication e where s.id =?1")
	public PharmacyStorageItem findOneWithMedication(Long pharmacyStorageItemId);
}
