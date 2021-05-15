package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.repository.ComplaintPharmacyRepository;

@Service
public class ComplaintPharmacyService {

	@Autowired
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	
	public List<ComplaintPharmacy> findAll() 
	{
		return complaintPharmacyRepository.findAll();
	}
	
	public void save(ComplaintPharmacy complaint)
	{
		complaintPharmacyRepository.save(complaint);
	}
	
}
