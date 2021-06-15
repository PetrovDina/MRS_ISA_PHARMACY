package mrsisa12.pharmacy.service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.MedicationRating;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.repository.MedicationRepository;

@Service
public class MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;
	
	@Autowired
	private MedicationRatingService medicationRatingService;
	
	@Autowired
	private PatientService patientService;
	
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

	public Double rateMedication(String patientUsername, Long medicationId, double ratedValue) throws IllegalArgumentException{
		if (ratedValue < 0 || ratedValue > 5) {
			throw new IllegalArgumentException();
 
		}
		Patient patient = patientService.findByUsername(patientUsername);
		Medication medication = this.findOne(medicationId);
		
		MedicationRating rating = medicationRatingService.findOneByPatientAndMedication(patientUsername, medicationId);
		
		if (rating == null) {
			//create new rating obj
			rating = new MedicationRating();
			rating.setDate(new Date());
			rating.setMedication(medication);
			rating.setPatient(patient);
			rating.setRating(ratedValue);
			medicationRatingService.save(rating);
		}
		else {
			//update existing rating obj
			rating.setRating(ratedValue);
			rating.setDate(new Date());
			medicationRatingService.save(rating);
		}
		
		//updating value in Medication object
		List<MedicationRating> medicationsRatings = medicationRatingService.findAllByMedication(medicationId);
		int numRatings = medicationsRatings.size();
		double newRating = 0;
		
		for (MedicationRating er : medicationsRatings) {
			newRating += er.getRating();
		}
		
		newRating /= numRatings;
		
		DecimalFormat df = new DecimalFormat("#.##");
		newRating = Double.parseDouble(df.format(newRating));
		
		medication.setRating(newRating);
		this.save(medication);
		
		return newRating;
	} 

}
