package mrsisa12.pharmacy.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.service.MedicationService;

@RestController
@RequestMapping("/med")
public class MedicationController {

	@Autowired
	private MedicationService medicationService;

	
	@GetMapping(value="/testAdd", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Medication>> addSomeTest() {
		Medication med = new Medication(null, "Aspirin");
		Medication med1 = new Medication(null, "Brufen");
		Medication med2 = new Medication(null, "Promasepan");
		try {
			medicationService.create(med);
			medicationService.create(med1);
			medicationService.create(med2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Collection<Medication> medication = medicationService.findAll();
		return new ResponseEntity<Collection<Medication>>(medication, HttpStatus.OK);
	}
	
	@GetMapping(value="/test", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Medication>> getMedications() {
		Collection<Medication> medication = medicationService.findAll();
		return new ResponseEntity<Collection<Medication>>(medication, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medication> getMedication(@PathVariable("id") Integer id) {
		Medication medication = medicationService.findOne(id);

		if (medication == null) {
			return new ResponseEntity<Medication>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Medication>(medication, HttpStatus.OK);
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medication> createMedication(@RequestBody Medication medication) throws Exception {
		Medication savedMedication = medicationService.create(medication);
		return new ResponseEntity<Medication>(savedMedication, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Medication> updateMedication(@RequestBody Medication medication, @PathVariable Integer id)
			throws Exception {
		Medication medicationForUpdate = medicationService.findOne(id);
		medicationForUpdate.copyValues(medication);

		Medication updatedMedication = medicationService.update(medicationForUpdate);

		if (updatedMedication == null) {
			return new ResponseEntity<Medication>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<Medication>(updatedMedication, HttpStatus.OK);
	}
}
