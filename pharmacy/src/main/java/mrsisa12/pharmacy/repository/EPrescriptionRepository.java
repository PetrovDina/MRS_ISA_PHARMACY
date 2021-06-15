package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.Patient;

public interface EPrescriptionRepository extends JpaRepository<EPrescription, Long> {

	EPrescription findOneByCode(String code);
	
	@Query("select distinct res from EPrescription res join fetch res.prescriptionItems pi where res.patient.username = ?1")
	List<EPrescription> findAllByPatientWithPrescriptionItems(String patientUsername);

	List<EPrescription> findAllByPatient(Patient patient);
}
