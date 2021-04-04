package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.service.MedicationService;

@RestController
@RequestMapping("/med")
public class MedicationController {

	@Autowired
	private MedicationService medicationService;

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

		// studen must exist
		if (medication == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new MedicationDTO(medication), HttpStatus.OK);
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
