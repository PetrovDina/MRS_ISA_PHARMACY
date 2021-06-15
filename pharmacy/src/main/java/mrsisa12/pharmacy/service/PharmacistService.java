package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.repository.PharmacistRepository;

@Service
public class PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;

	public Pharmacist findOne(Long id) {
		return pharmacistRepository.findById(id).orElse(null);
	}

	public List<Pharmacist> findAll() {
		return pharmacistRepository.findAll();
	}

	public void save(Pharmacist pharmacist) {
		pharmacistRepository.save(pharmacist);
	}

	@Transactional(readOnly = false)
	public void deletePharmacist(Pharmacist pharmacist) {
		pharmacistRepository.delete(pharmacist);
	}
	
	public Pharmacist findByUsername(String username) {
		return pharmacistRepository.findByUsername(username);
	}

}
