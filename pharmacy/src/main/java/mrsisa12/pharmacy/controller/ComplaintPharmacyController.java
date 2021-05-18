package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.complaint.dto.ComplaintAdminPharmacyDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintAdminPharmacyResponseDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintPharmacyDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintUserPharmacyDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintUserPharmacyResponseDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.service.ComplaintPharmacyService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.SystemAdminService;

@RestController
@RequestMapping("/complaintPharmacy")
public class ComplaintPharmacyController {

	@Autowired
	ComplaintPharmacyService complaintPharmacyService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	PharmacyService pharmacyService;
	
	@Autowired
	SystemAdminService systemAdminService;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<ComplaintPharmacyDTO> savePharmacyComplaint(@RequestBody ComplaintPharmacyDTO complaintDTO)
	{	
		ComplaintPharmacy complaintPharmacy = new ComplaintPharmacy();
		
		complaintPharmacy.setPatient(patientService.findByUsername(complaintDTO.getPatientUsername()));
		complaintPharmacy.setPharmacy(pharmacyService.findOne(complaintDTO.getPharmacyId()));
		complaintPharmacy.setContent(complaintDTO.getContent());
		
		complaintPharmacyService.save(complaintPharmacy);
		
		ComplaintPharmacyDTO ret = new ComplaintPharmacyDTO();
		ret.setId(complaintPharmacy.getId());
		ret.setPatientUsername(complaintPharmacy.getPatient().getUsername());
		ret.setPharmacyId(complaintPharmacy.getPharmacy().getId());
		ret.setContent(complaintPharmacy.getContent());
		
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/all/forResponse")
	public ResponseEntity<List<ComplaintAdminPharmacyDTO>> getPharmacyComplaintForResponse()
	{
		
		List<ComplaintPharmacy> complaints = complaintPharmacyService.findAllByAdminNull();
		List<ComplaintAdminPharmacyDTO> complaintDTOs = new ArrayList<ComplaintAdminPharmacyDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintPharmacy complaint : complaints) 
		{
			complaintDTOs.add(new ComplaintAdminPharmacyDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@PutMapping(value = "/update", consumes = "application/json")
	public ResponseEntity<ComplaintPharmacyDTO> updateComplaint(@RequestBody ComplaintPharmacyDTO complaintDTO) 
	{
		ComplaintPharmacy complaintPharmacy = complaintPharmacyService.findOneById(complaintDTO.getId());
		SystemAdmin systemAdmin = systemAdminService.findOneByUsername(complaintDTO.getSystemAdminUsername());
		
		complaintPharmacy.setResponse(complaintDTO.getResponse());
		complaintPharmacy.setSystemAdmin(systemAdmin);
		
		complaintPharmacyService.save(complaintPharmacy);
		
		Patient patient = patientService.findByUsername(complaintDTO.getPatientUsername());
		Pharmacy pharmacy = pharmacyService.findOne(complaintDTO.getPharmacyId());
		emailService.sendEmailToPatientComplaintPharmacyResponse(patient, systemAdmin, pharmacy);

		return new ResponseEntity<>(new ComplaintPharmacyDTO(complaintPharmacy), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all/{username}")
	public ResponseEntity<List<ComplaintAdminPharmacyResponseDTO>> getPharmacyComplaintResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintAdminPharmacyResponseDTO na osnovu admina
		 * */
		
		SystemAdmin systemAdmin = systemAdminService.findOneByUsername(username);
		List<ComplaintPharmacy> complaints = complaintPharmacyService.findAllBySystemAdmin(systemAdmin);
		List<ComplaintAdminPharmacyResponseDTO> complaintDTOs = new ArrayList<ComplaintAdminPharmacyResponseDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintPharmacy complaint : complaints) 
		{
			complaintDTOs.add(new ComplaintAdminPharmacyResponseDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/NotResponsed/{username}")
	public ResponseEntity<List<ComplaintUserPharmacyDTO>> getPharmacyComplaintUserNotResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintUserPharmacyDTO na koje admin nije odgovorio za pacijenta
		 * */
		
		Patient patient = patientService.findByUsername(username);
		List<ComplaintPharmacy> complaints = complaintPharmacyService.findAllByPatient(patient);
		List<ComplaintUserPharmacyDTO> complaintDTOs = new ArrayList<ComplaintUserPharmacyDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintPharmacy complaint : complaints) 
		{
			if(complaint.getSystemAdmin() == null)
				complaintDTOs.add(new ComplaintUserPharmacyDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/Responsed/{username}")
	public ResponseEntity<List<ComplaintUserPharmacyResponseDTO>> getPharmacyComplaintUserResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintUserPharmacyDTO na koje je admin odgovorio za pacijenta
		 * */
		
		Patient patient = patientService.findByUsername(username);
		List<ComplaintPharmacy> complaints = complaintPharmacyService.findAllByPatient(patient);
		List<ComplaintUserPharmacyResponseDTO> complaintDTOs = new ArrayList<ComplaintUserPharmacyResponseDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintPharmacy complaint : complaints) 
		{
			if(complaint.getSystemAdmin() != null)
				complaintDTOs.add(new ComplaintUserPharmacyResponseDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
}
