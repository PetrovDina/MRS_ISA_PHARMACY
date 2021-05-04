package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mrsisa12.pharmacy.model.Pharmacy;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

	public Page<Pharmacy> findAll(Pageable pageable);

	public List<Pharmacy> findAllByName(String name);

	public List<Pharmacy> findByNameAllIgnoringCase(String name);

	@Query("select s from Pharmacy s join fetch s.pharmacyStorageItems e where s.id =?1")
	public Pharmacy findOneWithStorageItems(Long pharmacyId);

	@Query("select s from Pharmacy s join fetch s.pharmacyStorageItems e where e.id =?1")
	public Pharmacy findOneByStorageItem(Long storageItemId);

	@Query("select s from Pharmacy s where lower(s.name) like lower(concat('%', :query,'%')) "
			+ "or lower(s.location.city)  like lower(concat('%', :query,'%')) "
			+ "or lower(s.location.street)  like lower(concat('%', :query,'%')) "
			+ "or lower(s.location.zipcode)  like lower(concat('%', :query,'%')) "
			+ "or cast(s.location.streetNum as string)  like lower(concat('%', :query,'%')) "
			+ "or cast(s.rating as string)  like lower(concat('%', :query,'%')) ")

	public List<Pharmacy> findByQuery(@Param("query") String query);

	@Query("select s from Pharmacy s join fetch s.employments e where s.id =?1")
	public Pharmacy findOneWithEmployments(Long id);

}
