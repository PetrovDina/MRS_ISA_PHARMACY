package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.enums.PatientCategory;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.RoleService;

@RestController
@RequestMapping("/patient")
public class PatientController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private EmailService emailService;
	

		
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/addAllergy")
	public ResponseEntity<PatientDTO> addAllergyToPatient(@RequestParam("patientUsername") String patientUsername, @RequestParam("allergyId") Long allergyId) {

		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);
		patient.getAllergies().add(medicationService.findOne(allergyId));
		patientService.save(patient);

		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/removeAllergy")
	public ResponseEntity<PatientDTO> removeAllergyFromPatient(@RequestParam("patientUsername") String patientUsername,
			@RequestParam("allergyId") Long allergyId) {

		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);
		Medication allergy = medicationService.findOne(allergyId);
		patient.getAllergies().remove(allergy);
		patientService.removeAllergy(patient.getId(), allergyId);
		//allergy.getAllergicPatients().remove(patient);
		patientService.save(patient);
		medicationService.save(allergy);

		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/getPatientsAllergies")
	public ResponseEntity<List<MedicationDTO>> getPatientsAllergies(@RequestParam("patientUsername") String patientUsername) {

		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);
		List<MedicationDTO> allergies = new ArrayList<MedicationDTO>();
		for (Medication m : patient.getAllergies()) {
			allergies.add(new MedicationDTO(m));
		}

		return new ResponseEntity<>(allergies, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/allowedMedications")
	public ResponseEntity<List<MedicationDTO>> getPatientsAllowedMedications(@RequestParam("patientUsername") String patientUsername) {

		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);
		List<Medication> allMeds = medicationService.findAll();
		
		List<MedicationDTO> allowed = new ArrayList<MedicationDTO>();
		for (Medication m : allMeds) {
			if (!isAllergy(m, patient.getAllergies())) {
				allowed.add(new MedicationDTO(m));

			}
			
		}

		return new ResponseEntity<>(allowed, HttpStatus.OK);
	}
	
	boolean isAllergy(Medication m, List<Medication> allergies ) {
		for (Medication allergy : allergies) {
			if (m.getId() == allergy.getId()) {
				return true;

			}
		}
		return false;
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/removeSubscription")
	public ResponseEntity<Void> removeSubscriptionFromPatient(@RequestParam("patientUsername") String patientUsername,
			@RequestParam("subscriptionId") Long subscriptionId) {

		patientService.removeSubscription(patientUsername, subscriptionId);
		
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/addSubscription")
	public ResponseEntity<Void> addSubscriptionFromPatient(@RequestParam("patientUsername") String patientUsername,
			@RequestParam("subscriptionId") Long subscriptionId) {

		patientService.addSubscription(patientUsername, subscriptionId);
		
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/getSubscriptions")
	public ResponseEntity<List<PharmacyDTO>> getPatientsSubsriptions(@RequestParam("patientUsername") String patientUsername) {

		Patient patient = patientService.findByUsernameWithSubscriptions(patientUsername);
		ArrayList<PharmacyDTO> subscriptions = new ArrayList<PharmacyDTO>();
		for (Pharmacy subscription : patient.getSubscriptions()) {
			subscriptions.add(new PharmacyDTO(subscription));
		}
		
		return new ResponseEntity<List<PharmacyDTO>>(subscriptions, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/isSubscribed")
	public ResponseEntity<Boolean> isSubscribed(@RequestParam("patientUsername") String patientUsername,
			@RequestParam("pharmacyId") Long pharmacyId) {

		Patient patient = patientService.findByUsernameWithSubscriptions(patientUsername);
		
		for (Pharmacy subscription : patient.getSubscriptions()) {
			if(subscription.getId() == pharmacyId) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}	
		}
		
		return new ResponseEntity<Boolean>(false, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<PatientDTO> updatePatient(@RequestBody PatientDTO patientDTO) {

		Patient p = patientService.findOne(patientDTO.getId());

		if (p == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		p.setFirstName(patientDTO.getFirstName());
		p.setLastName(patientDTO.getLastName());
		p.setUsername(patientDTO.getUsername());
		p.setLocation(patientDTO.getLocation());
		System.err.println(p.getLocation().getStreet());
		patientService.save(p);
		locationService.save(p.getLocation());
		return new ResponseEntity<>(new PatientDTO(p), HttpStatus.CREATED);
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/create", consumes = "application/json")
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
		pa.setLoggedFirstTime(true);
		pa.setPenaltyPoints(new Integer(0));
		pa.setLoyaltyPoints(new Integer(0));
		pa.setCategory(PatientCategory.REGULAR);
		
		try 
		{
			patientService.save(pa);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		
		emailService.confirmationEmailUserRegistration(pa);
		
		return new ResponseEntity<>(new UserDTO(pa), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<PatientDTO>> getAllPatients() {

		List<Patient> patients = patientService.findAll();

		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Patient p : patients) {
			patientsDTO.add(new PatientDTO(p));
		}

		return new ResponseEntity<>(patientsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<PatientDTO> getOneByUsername(@PathVariable("username") String username) {

		Patient patient = patientService.findByUsername(username);
		
		if (patient == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}



		return new ResponseEntity<>(new PatientDTO(patient), HttpStatus.OK);
	}

}
