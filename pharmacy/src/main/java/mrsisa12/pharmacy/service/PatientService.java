package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.repository.PatientRepository;

@Service
public class PatientService {
	
	@Autowired
	private PatientRepository patientRepository;
	
	public List<Patient> findAll() 
	{
		return patientRepository.findAll();
	}
	
	public Page<Patient> findAll(Pageable page) 
	{
		return patientRepository.findAll(page);
	}
	
	public List<Patient> findAllByUsername(String username)
	{
		return patientRepository.findAllByUsername(username);
	}
	
	public List<Patient> findAllByPenaltyPoints(Integer penaltyPoints)
	{
		return patientRepository.findAllByPenaltyPoints(penaltyPoints);
	}
	
	public void save(Patient patient)
	{
		patientRepository.save(patient);
	}

}
