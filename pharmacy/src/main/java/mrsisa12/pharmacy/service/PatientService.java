package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PharmacyService pharmacyService;

	public List<Patient> findAll() {
		return patientRepository.findAll();
	}

	public Page<Patient> findAll(Pageable page) {
		return patientRepository.findAll(page);
	}

	public List<Patient> findAllByUsername(String username) {
		return patientRepository.findAllByUsername(username);
	}

	public List<Patient> findAllByPenaltyPoints(Integer penaltyPoints) {
		return patientRepository.findAllByPenaltyPoints(penaltyPoints);
	}

	public void save(Patient patient) {
		patientRepository.save(patient);
	}

	public Patient findOne(Long id) {
		return patientRepository.findById(id).orElseGet(null);
	}
	
	public Patient findByUsername(String username) {
		return patientRepository.findByUsername(username);
	}

	public Patient findByUsernameWithAllergies(String patientUsername) {
		return patientRepository.findByUsernameWithAllergies(patientUsername);
	}

	public void removeAllergy(Long patientId, Long allergyId) {
		patientRepository.removeAllergy(patientId, allergyId);
		
	}

	public Patient findByUsernameWithSubscriptions(String patientUsername) {
		return patientRepository.findByUsernameWithSubscriptions(patientUsername);
	}

	
	@Transactional
	public void removeSubscription(String patientUsername, Long subscriptionId) {
		Patient patient = this.findByUsernameWithSubscriptions(patientUsername);
		Pharmacy subscription = pharmacyService.findOneWithSubscribedPatients(subscriptionId);

		patient.getSubscriptions().remove(subscription);
		subscription.getSubscribedPatients().remove(patient);
		
		this.save(patient);
		pharmacyService.save(subscription);
		
	}
	
	public void addSubscription(String patientUsername, Long subscriptionId) {
		Patient patient = this.findByUsernameWithSubscriptions(patientUsername);
		Pharmacy subscription = pharmacyService.findOneWithSubscribedPatients(subscriptionId);

		patient.getSubscriptions().add(subscription);
		//subscription.getSubscribedPatients().add(patient); //da li ovde isto dodajem?

		this.save(patient);
		pharmacyService.save(subscription);

	}


}
