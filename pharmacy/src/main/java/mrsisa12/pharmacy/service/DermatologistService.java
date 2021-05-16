package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.repository.DermatologistRepository;

@Service
public class DermatologistService {

	@Autowired
	private DermatologistRepository dermatologistRepository;
	
	public List<Dermatologist> findAll() 
	{
		return dermatologistRepository.findAll();
	}
	
	public void save(Dermatologist dermatologist)
	{
		dermatologistRepository.save(dermatologist);
	}

	public Dermatologist findByUsername(String username) 
	{
		return dermatologistRepository.findOneByUsername(username);
	}
	
}
