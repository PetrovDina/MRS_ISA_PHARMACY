package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.PatientService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PatientDTO>> getAllPharmacies() {

		List<Patient> patients = patientService.findAll();

		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
			patientsDTO.add(new PatientDTO(p));
		}

		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDTO> savePharmacyAdmin(@RequestBody UserDTO patientDTO)
	{
		Patient pa = new Patient();
		pa.setUsername(patientDTO.getUsername());
		pa.setPassword(patientDTO.getPassword());
		pa.setEmail(patientDTO.getEmail());
		pa.setFirstName(patientDTO.getFirstName());
		pa.setLastName(patientDTO.getLastName());
		pa.setLocation(patientDTO.getLocation());
		pa.setGender(patientDTO.getGender());
		pa.setActiveStatus(UserStatus.UNVERIFIED);
		pa.setUserRole(patientDTO.getUserRole());
		pa.setDeleted(false);
		pa.setPenaltyPoints(new Integer(0));
		patientService.save(pa);
		
		return new ResponseEntity<>(new UserDTO(pa), HttpStatus.CREATED);
	}
	

}
