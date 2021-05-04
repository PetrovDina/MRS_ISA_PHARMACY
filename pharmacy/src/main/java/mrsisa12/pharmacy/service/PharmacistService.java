package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.repository.PharmacistRepository;

@Service
public class PharmacistService {

	@Autowired
	private PharmacistRepository pharmacistRepository;

	public List<Pharmacist> findAll() {
		return pharmacistRepository.findAll();
	}

	public void save(Pharmacist pharmacist) {
		pharmacistRepository.save(pharmacist);
	}

}
