package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Therapy;

public interface TherapyRepository  extends JpaRepository<Therapy, Long>{
	
	public List<Therapy> findAll();
	
	public Page<Therapy> findAll(Pageable pageable);
	
	@Query("select res from Therapy res where res.patient.username = ?1")
	public List<Therapy> findAllByPatient(String patientUserName);
	
	@Query("select distinct res from Therapy res join fetch res.prescriptionItems pi where res.patient.username = ?1")
	public List<Therapy> findAllByPatientWithPrescriptionItems(String patientUserName);

	@Query("select distinct res from Therapy res join fetch res.prescriptionItems pi where res.status = 'COMPLETED' and res.pharmacy = ?1")
	public List<Therapy> findAllByPharmacyCompleted(Pharmacy pharmacy);


}
