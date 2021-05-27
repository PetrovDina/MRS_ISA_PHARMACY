
package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.MedicationRating;



public interface MedicationRatingRepository extends JpaRepository<MedicationRating, Long>{

	public List<MedicationRating> findAll();

	@Query("select rat from MedicationRating rat where rat.patient.username = ?1 and rat.medication.id = ?2")
	public MedicationRating findOneByPatientAndMedication(String patientUsername, Long medicationId);

	@Query("select rat from MedicationRating rat where rat.medication.id = ?1")
	public List<MedicationRating> findAllByMedication(Long medicationId);

}

