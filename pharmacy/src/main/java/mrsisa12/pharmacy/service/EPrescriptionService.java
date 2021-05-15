package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.repository.EPrescriptionRepository;

@Service
public class EPrescriptionService {

	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;
	
	public EPrescription findOne(Long id) {
		return ePrescriptionRepository.findById(id).orElseGet(null);
	}
	
	public Page<EPrescription> findAll(Pageable page) {
		return ePrescriptionRepository.findAll(page);
	}
	
	public List<EPrescription> findAll() {
		return ePrescriptionRepository.findAll();
	}
	
	public EPrescription save(EPrescription ePrescription) {
		return ePrescriptionRepository.save(ePrescription);
	}
	
	public List<EPrescription> findAllByPatient(String patientUsername) {
		return ePrescriptionRepository.findAllByPatient(patientUsername);
	}
	
}
