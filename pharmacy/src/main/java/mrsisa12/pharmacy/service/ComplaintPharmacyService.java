package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.SystemAdmin;
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
	
	public List<ComplaintPharmacy> findAllByAdminNull()
	{
		return complaintPharmacyRepository.findAllByAdminNull();
	}
	
	public ComplaintPharmacy findOneById(Long id) 
	{
		return complaintPharmacyRepository.findOneById(id);
	}
	
	public List<ComplaintPharmacy> findAllBySystemAdmin(SystemAdmin systemAdmin)
	{
		return complaintPharmacyRepository.findAllBySystemAdmin(systemAdmin);
	}
	
	public List<ComplaintPharmacy> findAllByPatient(Patient patient)
	{
		return complaintPharmacyRepository.findAllByPatient(patient);
	}
	
}
