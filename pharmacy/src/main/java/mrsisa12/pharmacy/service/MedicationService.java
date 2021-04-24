package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	public Medication findOne(Long id) {
		return medicationRepository.findById(id).orElseGet(null);
	}

	public List<Medication> findAll() {
		return medicationRepository.findAll();
	}
	
	public Page<Medication> findAll(Pageable page) {
		return medicationRepository.findAll(page);
	}

	public Medication save(Medication medication) {
		return medicationRepository.save(medication);
	}

	public void remove(Long id) {
		medicationRepository.deleteById(id);
	}
	
	public List<Medication> findAllByName(String name) {
		return medicationRepository.findAllByName(name);
	}
	
	public List<Medication> findByNameAllIgnoringCase(String name) {
		return medicationRepository.findByNameAllIgnoringCase(name);
	}
	
	public List<Medication> findAllByManufacturer(String manufacturer) {
		return medicationRepository.findAllByManufacturer(manufacturer);
	}
	
	public List<Medication> findAllByPrescriptionReq(Boolean prescriptionReq) {
		return medicationRepository.findAllByPrescriptionReq(prescriptionReq);
	}
	
	public Medication findOneWithAlternatives(Long id) {
		return medicationRepository.findByIdWithAlternatives(id);
	} 

}
