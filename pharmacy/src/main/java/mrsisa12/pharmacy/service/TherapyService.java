package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.repository.TherapyRepository;

@Service
public class TherapyService {

	@Autowired
	private TherapyRepository ePrescriptionRepository;
	
	public Therapy findOne(Long id) {
		return ePrescriptionRepository.findById(id).orElseGet(null);
	}
	
	public Page<Therapy> findAll(Pageable page) {
		return ePrescriptionRepository.findAll(page);
	}
	
	public List<Therapy> findAll() {
		return ePrescriptionRepository.findAll();
	}
	
	public Therapy save(Therapy ePrescription) {
		return ePrescriptionRepository.save(ePrescription);
	}
	
	public List<Therapy> findAllByPatient(String patientUsername) {
		return ePrescriptionRepository.findAllByPatient(patientUsername);
	}
	
	public List<Therapy> findAllNewByPatientWithPrescriptionItems(String patientUsername) {
		return ePrescriptionRepository.findAllNewByPatientWithPrescriptionItems(patientUsername);
	}
	
	public List<Therapy> findAllFilledByPatientWithPrescriptionItems(String patientUsername) {
		return ePrescriptionRepository.findAllFilledByPatientWithPrescriptionItems(patientUsername);
	}
	
}
