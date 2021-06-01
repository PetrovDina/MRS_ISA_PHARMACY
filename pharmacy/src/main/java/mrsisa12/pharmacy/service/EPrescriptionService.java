package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.repository.EPrescriptionRepository;

@Service
public class EPrescriptionService {
	
	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;

	public EPrescription save(EPrescription ePrescription) 
	{
		return ePrescriptionRepository.save(ePrescription);
	}

	public List<EPrescription> findAllByPatientWithPrescriptionItems(String patientUsername) 
	{
		return ePrescriptionRepository.findAllByPatientWithPrescriptionItems(patientUsername); 
	}
	
	public EPrescription findOneByCode(String code)
	{
		return ePrescriptionRepository.findOneByCode(code);
	}
	
	public List<EPrescription>  findAllByPatient(Patient patient)
	{
		return ePrescriptionRepository.findAllByPatient(patient);
	}
	
}
