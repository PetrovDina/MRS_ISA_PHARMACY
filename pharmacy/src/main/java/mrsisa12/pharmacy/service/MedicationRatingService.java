
package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.MedicationRating;
import mrsisa12.pharmacy.repository.MedicationRatingRepository;

@Service
public class MedicationRatingService {

	@Autowired
	private MedicationRatingRepository medicationRatingRepository;

	public void save(MedicationRating pr)
	{
		medicationRatingRepository.save(pr);
	}
	
	public MedicationRating findOne(Long id) {
		return medicationRatingRepository.findById(id).orElseGet(null);
	}

	public List<MedicationRating> findAll() {
		return medicationRatingRepository.findAll();
	}

	public MedicationRating findOneByPatientAndMedication(String patientUsername, Long medicationId) {
		return medicationRatingRepository.findOneByPatientAndMedication(patientUsername, medicationId);
	}

	public List<MedicationRating> findAllByMedication(Long medicationId) {
		return medicationRatingRepository.findAllByMedication(medicationId);
	}


}
