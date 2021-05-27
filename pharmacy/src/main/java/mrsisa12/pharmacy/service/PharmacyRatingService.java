
package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.PharmacyRating;
import mrsisa12.pharmacy.repository.PharmacyRatingRepository;

@Service
public class PharmacyRatingService {

	@Autowired
	private PharmacyRatingRepository pharmacyRatingRepository;

	public void save(PharmacyRating pr)
	{
		pharmacyRatingRepository.save(pr);
	}
	
	public PharmacyRating findOne(Long id) {
		return pharmacyRatingRepository.findById(id).orElseGet(null);
	}

	public List<PharmacyRating> findAll() {
		return pharmacyRatingRepository.findAll();
	}

	public PharmacyRating findOneByPatientAndPharmacy(String patientUsername, Long pharmacyId) {
		return pharmacyRatingRepository.findOneByPatientAndPharmacy(patientUsername, pharmacyId);
	}

	public List<PharmacyRating> findAllByPharmacy(Long pharmacyId) {
		return pharmacyRatingRepository.findAllByPharmacy(pharmacyId);
	}
}
