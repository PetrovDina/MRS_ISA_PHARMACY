package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.repository.DermatologistRepository;

@Service
public class DermatologistService {

	@Autowired
	private DermatologistRepository dermatoligistRepository;

	public List<Dermatologist> findAll() {
		return dermatoligistRepository.findAll();
	}
}
