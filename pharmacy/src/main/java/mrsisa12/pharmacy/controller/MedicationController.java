package mrsisa12.pharmacy.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.MedicationCreationDTO;
import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.dto.MedicationDetailsDTO;
import mrsisa12.pharmacy.dto.MedicationQrDTO;
import mrsisa12.pharmacy.dto.MedicationQrTableDTO;
import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.EPrescriptionItem;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.MedicationRating;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.service.EPrescriptionService;
import mrsisa12.pharmacy.service.MedicationRatingService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.ReservationService;

@RestController
@RequestMapping("/med")
public class MedicationController {

	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private MedicationRatingService medicationRatingService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private EPrescriptionService ePrescriptionService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<MedicationDTO>> getAllMedications() {

		List<Medication> medications = medicationService.findAll();

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}

		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/forPharmacyToAdd/{id}")
	public ResponseEntity<List<MedicationDTO>> getAllMedicationsToAddIntoPharmacy(@PathVariable Long id) {

		List<Medication> medications = medicationService.findAll();

		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(id);
		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		boolean found = false;
		for (Medication medication : medications) {
			found = false;
			for (PharmacyStorageItem psi : pharmacy.getPharmacyStorageItems()) {
				if(psi.getMedication().getId() == medication.getId())
					found = true;
			}
			if(!found)
				medicationsDTO.add(new MedicationDTO(medication));
		}

		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	public ResponseEntity<List<MedicationDTO>> allPatientNotAllergic(@RequestParam String username) {

		List<Medication> medications = medicationService.findAll();

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}

		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<MedicationCreationDTO> saveMedication(@RequestBody MedicationCreationDTO medicationDTO)
	{
		Medication medication = new Medication();
		medication.setName(medicationDTO.getName());
		medication.setContent(medicationDTO.getContent());
		medication.setManufacturer(medicationDTO.getManufacturer());
		medication.setPrescriptionReq(medicationDTO.isPrescriptionReq());
		medication.setDescription(medicationDTO.getDescription());
		medication.setForm(medicationDTO.getForm());
		medication.setRating(0);
		medication.setLoyaltyPoints(medicationDTO.getLoyaltyPoints());
		
		List<Medication> alternatives = new ArrayList<Medication>();
		for (MedicationDTO m : medicationDTO.getAlternatives()) 
		{
			alternatives.add(medicationService.findOne(m.getId()));
		}
		medication.setAlternatives(alternatives);
		medicationService.save(medication);
		
		return new ResponseEntity<>(new MedicationCreationDTO(medication), HttpStatus.CREATED);
	}
	
	
	@GetMapping
	public ResponseEntity<List<MedicationDTO>> getMedicationsPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<Medication> medications = medicationService.findAll(page);

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}

		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicationDTO> getMedication(@PathVariable Long id) {

		Medication medication = medicationService.findOne(id);

		// medication must exist
		if (medication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new MedicationDTO(medication), HttpStatus.OK);
	}
	
	@GetMapping(value = "details/{id}")
	public ResponseEntity<MedicationDetailsDTO> getMedicationDetails(@PathVariable Long id) {
		Medication medication = medicationService.findOneWithAlternatives(id);
		if (medication == null) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (medication.getAlternatives() == null)
		{
			medication.setAlternatives(new ArrayList<Medication>());
		}

		return new ResponseEntity<>(new MedicationDetailsDTO(medication), HttpStatus.OK);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<MedicationDTO> saveMedication(@RequestBody MedicationDTO medicationDTO) {

		Medication medication = new Medication();
		
		medication.setName(medicationDTO.getName());
		medication.setManufacturer(medicationDTO.getManufacturer());
		medication.setPrescriptionReq(medicationDTO.isPrescriptionReq());
		medication.setForm(medicationDTO.getForm());

		
		medication = medicationService.save(medication);
		return new ResponseEntity<>(new MedicationDTO(medication), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<MedicationDTO> updateMedication(@RequestBody MedicationDTO medicationDTO) {

		// a medication must exist
		Medication medication = medicationService.findOne(medicationDTO.getId());

		if (medication == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		medication.setName(medicationDTO.getName());
		medication.setManufacturer(medicationDTO.getManufacturer());
		medication.setPrescriptionReq(medicationDTO.isPrescriptionReq());
		medication.setForm(medicationDTO.getForm());
		
		medication = medicationService.save(medication);
		return new ResponseEntity<>(new MedicationDTO(medication), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteMedication(@PathVariable Long id) {

		Medication medication = medicationService.findOne(id);

		if (medication != null) {
			medicationService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// OVO CE NAM TREBATI KAD BUDEMO IMALI UNIQUE PODATAK O LIJEKU
//	@GetMapping(value = "/findName")
//	public ResponseEntity<MedicationDTO> getMedicationByIndex(@RequestParam String index) {
//
//		Medication medication = medicationService.findByIndex(index);
//		if (medication == null) {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//		return new ResponseEntity<>(new MedicationDTO(medication), HttpStatus.OK);
//	}
	
	@GetMapping(value = "/findName")
	public ResponseEntity<List<MedicationDTO>> getMedicationsByName(@RequestParam String name) {

		List<Medication> medications = medicationService.findAllByName(name);

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}
		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/findManufacturer")
	public ResponseEntity<List<MedicationDTO>> getMedicationsByManufacturer(@RequestParam String manufacturer) {

		List<Medication> medications = medicationService.findAllByManufacturer(manufacturer);

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}
		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getAllPresReq")
	public ResponseEntity<List<MedicationDTO>> getMedicationsByManufacturer(@RequestParam Boolean prescriptionReq) {

		List<Medication> medications = medicationService.findAllByPrescriptionReq(prescriptionReq);

		// convert medications to DTOs
		List<MedicationDTO> medicationsDTO = new ArrayList<>();
		for (Medication m : medications) {
			medicationsDTO.add(new MedicationDTO(m));
		}
		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/getRating")
	public ResponseEntity<Double> getRating(
			@RequestParam String patientUsername, @RequestParam Long medicationId) {


		MedicationRating rating = medicationRatingService.findOneByPatientAndMedication(patientUsername, medicationId);
		
		if (rating == null) {
			return new ResponseEntity<Double>(0.0, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Double>(rating.getRating(), HttpStatus.OK);	
		}

	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/checkCanRate")
	public ResponseEntity<Boolean> checkCanRate(
			@RequestParam String patientUsername, @RequestParam Long medicationId) {


		MedicationRating rating = medicationRatingService.findOneByPatientAndMedication(patientUsername, medicationId);
		
		//if a rating already exists, that means the patient can rate the pharmacy
		if (rating != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		else {
			//checking if any reservation exist witht this medication
			List<Reservation> reservations = reservationService.findAllCompletedByPatientAndMedication(patientUsername, medicationId);
			if (reservations.size() > 0) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}

			//checking if any ePrescriptions exist with this medication
			List<EPrescription> prescriptions = ePrescriptionService.findAllByPatientWithPrescriptionItems(patientUsername);
			for (EPrescription pres : prescriptions) {
				for (EPrescriptionItem item : pres.getPrescriptionItems()) {
					if (item.getMedication().getId() == medicationId) {
						return new ResponseEntity<Boolean>(true, HttpStatus.OK);

					}
				}
			}
			
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);	
		}

	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/rateMedication")
	public ResponseEntity<Double> rateMedication(
			@RequestParam String patientUsername, @RequestParam Long medicationId, @RequestParam double ratedValue) {

		if (ratedValue < 0 || ratedValue > 5) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
 
		}
		Patient patient = patientService.findByUsername(patientUsername);
		Medication medication = medicationService.findOne(medicationId);
		
		MedicationRating rating = medicationRatingService.findOneByPatientAndMedication(patientUsername, medicationId);
		
		if (rating == null) {
			//create new rating obj
			rating = new MedicationRating();
			rating.setDate(new Date());
			rating.setMedication(medication);
			rating.setPatient(patient);
			rating.setRating(ratedValue);
			medicationRatingService.save(rating);
		}
		else {
			//update existing rating obj
			rating.setRating(ratedValue);
			rating.setDate(new Date());
			medicationRatingService.save(rating);
		}
		
		//updating value in Medication object
		List<MedicationRating> medicationsRatings = medicationRatingService.findAllByMedication(medicationId);
		int numRatings = medicationsRatings.size();
		double newRating = 0;
		
		for (MedicationRating er : medicationsRatings) {
			newRating += er.getRating();
		}
		
		newRating /= numRatings;
		
		DecimalFormat df = new DecimalFormat("#.##");
		newRating = Double.parseDouble(df.format(newRating));
		
		medication.setRating(newRating);
		medicationService.save(medication);

		return new ResponseEntity<Double>(newRating, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping(value = "/getQrSearch", consumes = "application/json")
	public ResponseEntity<List<MedicationQrTableDTO>> getMedicationsByQrSearch(@RequestBody QrCodeDTO medications) {

		List<MedicationQrTableDTO> medicationDTOs = new ArrayList<MedicationQrTableDTO>();
		
		for (MedicationQrDTO med : medications.getMedications()) 
		{
			Medication medication = medicationService.findOne(med.getId());
			medicationDTOs.add(new MedicationQrTableDTO(medication, med.getQuantity()));
		}

		return new ResponseEntity<List<MedicationQrTableDTO>>(medicationDTOs, HttpStatus.OK);	
	}
	
//	
//	@GetMapping(value="/testAdd", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Collection<Medication>> addSomeTest() {
//		Medication med = new Medication(null, "Aspirin");
//		Medication med1 = new Medication(null, "Brufen");
//		Medication med2 = new Medication(null, "Promasepan");
//		try {
//			medicationService.create(med);
//			medicationService.create(med1);
//			medicationService.create(med2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		Collection<Medication> medication = medicationService.findAll();
//		return new ResponseEntity<Collection<Medication>>(medication, HttpStatus.OK);
//	}
	
//	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Collection<Medication>> getMedications() {
//		Collection<Medication> medication = medicationService.findAll();
//		return new ResponseEntity<Collection<Medication>>(medication, HttpStatus.OK);
//	}
//	
//	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Medication> getMedication(@PathVariable("id") Integer id) {
//		Medication medication = medicationService.findOne(id);
//
//		if (medication == null) {
//			return new ResponseEntity<Medication>(HttpStatus.NOT_FOUND);
//		}
//		
//		return new ResponseEntity<Medication>(medication, HttpStatus.OK);
//	}
//	
//	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) throws Exception {
//		Medication savedMedication = medicationService.create(medication);
//		return new ResponseEntity<Medication>(savedMedication, HttpStatus.CREATED);
//	}
//	
//	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication, @PathVariable Integer id)
//			throws Exception {
//		Medication medicationForUpdate = medicationService.findOne(id);
//		medicationForUpdate.copyValues(medication);
//
//		Medication updatedMedication = medicationService.update(medicationForUpdate);
//
//		if (updatedMedication == null) {
//			return new ResponseEntity<Medication>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		
//		return new ResponseEntity<Medication>(updatedMedication, HttpStatus.OK);
//	}
}
