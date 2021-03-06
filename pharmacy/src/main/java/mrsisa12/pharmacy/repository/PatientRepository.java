package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
	
	public Page<Patient> findAll(Pageable pageable);

	/*
	 * Pronalazi sve objekte tipa Patient i vraca onoliko objekata koliko je
	 * specificirano kroz Pageable objekat. Npr. ako se prosledi objekat: new
	 * PageRequest(0, 10) vratice se nulta stranica sa prvih 10 objekata tipa
	 * Patient. Vise informacija na:
	 * http://docs.spring.io/autorepo/docs/spring-data-commons/1.10.0.RC1/api/org/
	 * springframework/data/domain/PageRequest.html
	 */
	
	public List<Patient> findAllByUsername(String username);

	/*
	 * Vraca objekat po tacnom imenu i prezimenu ignorisuci mala i velika slova
	 */
	
	
	@Query("select pat from Patient pat where pat.penaltyPoints = ?1")
	public List<Patient> findAllByPenaltyPoints(Integer penaltyPoints);

	public Patient findByUsername(String username);

	@Query("select pat from Patient pat left join fetch pat.allergies al where pat.username = ?1")
	public Patient findByUsernameWithAllergies(String patientUsername);

    @Transactional
	@Modifying
	@Query(value = "delete from allergies alg where alg.patient_id = ?1 and alg.medication_id = ?2", nativeQuery = true)
	public void removeAllergy(Long patientId, Long allergyId);

	@Query("select pat from Patient pat left join fetch pat.subscriptions al where pat.username = ?1")
	public Patient findByUsernameWithSubscriptions(String patientUsername);
	
	@Modifying
	@Query("update Patient pat set pat.category='REGULAR' where pat.loyaltyPoints <= ?1")
	public void updateRegularCategory(Integer regularPoints);
	
	@Modifying
	@Query("update Patient pat set pat.category='SILVER' where pat.loyaltyPoints > ?1 and pat.loyaltyPoints <= ?2")
	public void updateSilverCategory(Integer regularPoints, Integer silverPoints);
	
	@Modifying
	@Query("update Patient pat set pat.category='GOLD' where pat.loyaltyPoints > ?1")
	public void updateGoldCategory(Integer silverPoints);
}








