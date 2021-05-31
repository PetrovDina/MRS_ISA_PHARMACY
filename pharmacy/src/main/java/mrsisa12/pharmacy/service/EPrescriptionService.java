package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.repository.EPrescriptionRepository;

@Service
public class EPrescriptionService {
	
	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;

	public EPrescription save(EPrescription ePrescription) 
	{
		return ePrescriptionRepository.save(ePrescription);
	}
}
