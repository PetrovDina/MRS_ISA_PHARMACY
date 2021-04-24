package mrsisa12.pharmacy.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Medication;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

	public Page<Medication> findAll(Pageable pageable);

	/*
	 * Pronalazi sve objekte tipa Medication i vraca onoliko objekata koliko je
	 * specificirano kroz Pageable objekat. Npr. ako se prosledi objekat: new
	 * PageRequest(0, 10) vratice se nulta stranica sa prvih 10 objekata tipa
	 * Medication. Vise informacija na:
	 * http://docs.spring.io/autorepo/docs/spring-data-commons/1.10.0.RC1/api/org/
	 * springframework/data/domain/PageRequest.html
	 */
	
	public List<Medication> findAllByName(String name);

	/*
	 * Vraca objekat po tacnom imenu i prezimenu ignorisuci mala i velika slova
	 */
	
	public List<Medication> findByNameAllIgnoringCase(String name);
	
	
	// @Query("select med from Medication med where med.manufacturer = ?1")
	public List<Medication> findAllByManufacturer(String manufacturer);
	
	@Query("select med from Medication med where med.prescriptionReq = ?1")
	public List<Medication> findAllByPrescriptionReq(Boolean prescriptionReq);

	@Query("select med from Medication med left join fetch med.alternatives e where med.id =?1")
	public Medication findByIdWithAlternatives(Long id);
	
}
