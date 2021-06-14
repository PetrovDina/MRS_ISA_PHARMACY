package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.repository.ComplaintPharmacyRepository;

@Service
public class ComplaintPharmacyService {

	@Autowired
	private ComplaintPharmacyRepository complaintPharmacyRepository;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	PharmacyService pharmacyService;
	
	@Autowired
	SystemAdminService systemAdminService;
	
	@Autowired
	EmailService emailService;
	
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
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean responseToComplaint(ComplaintPharmacy complaintPharmacy, SystemAdmin systemAdmin, String response,
			String patientUsername, Long pharmacyId)
	{
		
		if(complaintPharmacy.getResponse() != null)
		{
			return false;
		}
		
		complaintPharmacy.setResponse(response);
		complaintPharmacy.setSystemAdmin(systemAdmin);
		
		save(complaintPharmacy);
		
		Patient patient = patientService.findByUsername(patientUsername);
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
		emailService.sendEmailToPatientComplaintPharmacyResponse(patient, systemAdmin, pharmacy);
		
		return true;
	}
	
}