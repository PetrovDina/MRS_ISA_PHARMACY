package mrsisa12.pharmacy.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import mrsisa12.pharmacy.service.RoleService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PatientDTO>> getAllPharmacies() {

		List<Patient> patients = patientService.findAll();

		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
			patientsDTO.add(new PatientDTO(p));
		}

		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/create", consumes = "application/json")
//	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	public ResponseEntity<UserDTO> savePatient(@RequestBody UserDTO patientDTO)
	{
		Patient pa = new Patient();
		pa.setUsername(patientDTO.getUsername());
		pa.setPassword(passwordEncoder.encode(patientDTO.getPassword()));
		pa.setEmail(patientDTO.getEmail());
		pa.setFirstName(patientDTO.getFirstName());
		pa.setLastName(patientDTO.getLastName());
		pa.setLocation(patientDTO.getLocation());
		pa.setGender(patientDTO.getGender());
		pa.setActiveStatus(UserStatus.UNVERIFIED);
		pa.setRoles(roleService.findByName("ROLE_PATIENT"));
		pa.setDeleted(false);
		pa.setPenaltyPoints(new Integer(0));
		patientService.save(pa);
		
		return new ResponseEntity<>(new UserDTO(pa), HttpStatus.CREATED);
	}
}
