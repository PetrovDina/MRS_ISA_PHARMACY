package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ComplaintPharmacyDTO;
import mrsisa12.pharmacy.model.ComplaintPharmacy;
import mrsisa12.pharmacy.service.ComplaintPharmacyService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;

@RestController
@RequestMapping("/complaintPharmacy")
public class ComplaintPharmacyController {

	@Autowired
	ComplaintPharmacyService complaintPharmacyService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	PharmacyService pharmacyService;
	
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
	
}
