package mrsisa12.pharmacy.repository;

import java.util.List;

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
	
	@Query("select s from PharmacyStorageItem s join fetch s.itemPrices e where s.medication.id =?1 and e.current = true") 
	public List<PharmacyStorageItem> findAllWithCurrentPriceByMedication(Long medicationId);

	@Query("select s from PharmacyStorageItem s join fetch s.medication e where s.id =?1")
	public PharmacyStorageItem findOneWithMedication(Long pharmacyStorageItemId);

	@Query("select s from PharmacyStorageItem s where s.medication.id =?1 and s.pharmacy.id =?2")
	public PharmacyStorageItem findOneWithMedicationAndPharmacy(Long medicationId, Long pharmacyId);
	
	@Query("select s from PharmacyStorageItem s where s.pharmacy.id =?1 and s.counter > 0")
	public List<PharmacyStorageItem> findAllOutOfStock(Long pharmacyId);
}
