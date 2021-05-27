
package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.PharmacyRating;



public interface PharmacyRatingRepository extends JpaRepository<PharmacyRating, Long>{

	public List<PharmacyRating> findAll();

	@Query("select rat from PharmacyRating rat where rat.patient.username = ?1 and rat.pharmacy.id = ?2")
	public PharmacyRating findOneByPatientAndPharmacy(String patientUsername, Long pharmacyId);

	@Query("select rat from PharmacyRating rat where rat.pharmacy.id = ?1")
	public List<PharmacyRating> findAllByPharmacy(Long pharmacyId);

}

