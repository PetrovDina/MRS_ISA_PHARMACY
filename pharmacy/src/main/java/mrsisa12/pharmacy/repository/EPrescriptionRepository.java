package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.EPrescription;

public interface EPrescriptionRepository  extends JpaRepository<EPrescription, Long>{
	
	public List<EPrescription> findAll();
	
	public Page<EPrescription> findAll(Pageable pageable);
	
	@Query("select res from EPrescription res where res.patient.username = ?1")
	public List<EPrescription> findAllByPatient(String patientUserName);
	
	@Query("select distinct res from EPrescription res join fetch res.prescriptionItems pi where res.patient.username = ?1")
	public List<EPrescription> findAllByPatientWithPrescriptionItems(String patientUserName);

	@Query("select distinct res from EPrescription res join fetch res.prescriptionItems pi where res.status = 'COMPLETED' and res.patient.username = ?1")

	public List<EPrescription> findAllFilledByPatientWithPrescriptionItems(String patientUsername);
	
	@Query("select distinct res from EPrescription res join fetch res.prescriptionItems pi where res.status = 'CREATED' and res.patient.username = ?1")
	public List<EPrescription> findAllNewByPatientWithPrescriptionItems(String patientUsername);

}
