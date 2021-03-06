package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.repository.PatientRepository;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;

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
	
	public void addPointsAndUpdateCategory(Patient patient, Integer points) 
	{
		patient.setLoyaltyPoints(patient.getLoyaltyPoints() + points);
		patient.setCategory(loyaltyProgramService.getPatientCategory(patient));
		this.save(patient);
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
	
    @Transactional(propagation = Propagation.REQUIRED)
	@EventListener(ApplicationReadyEvent.class)
	public void resetPenals() {
    	if (LocalDate.now().getDayOfMonth() == 1) {
    		System.out.println("--FIRST OF MONTH: RESETING PATIENT PENALTY POINTS--");
    		List<Patient> patients = this.findAll();
    		for (Patient p : patients) {
    			p.setPenaltyPoints(0);
    			this.save(p);
    			
    		}
    	}
	}
	
	@Transactional
	public void updateCategories(Integer regularPoints, Integer silverPoints)
	{
		patientRepository.updateRegularCategory(regularPoints);
		patientRepository.updateSilverCategory(regularPoints, silverPoints);
		patientRepository.updateGoldCategory(silverPoints);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Patient updatePatient(PatientDTO patientDTO) {
		
		Patient p = this.findOne(patientDTO.getId());
		if (p == null) {
			return null;
		}

		p.setFirstName(patientDTO.getFirstName());
		p.setLastName(patientDTO.getLastName());
		p.setUsername(patientDTO.getUsername());
		p.setLocation(patientDTO.getLocation());
		this.save(p);
		locationService.save(p.getLocation());
		return p;
	}


}
