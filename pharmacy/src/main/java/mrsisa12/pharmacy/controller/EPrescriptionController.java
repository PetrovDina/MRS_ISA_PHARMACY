package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.MedicationQrDTO;
import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.EPrescriptionItem;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.service.EPrescriptionService;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;

@RestController
@RequestMapping("/ePrescription")
public class EPrescriptionController {
	
	@Autowired
	EPrescriptionService ePrescriptionService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;

	
    @PostMapping(value = "/buyMedicationsQr", consumes = "application/json")
	public ResponseEntity<String> buyMedicationsByQrSearch(@RequestBody QrCodeDTO medications, 
			@RequestParam("pharmacyId") Long pharmacyId, @RequestParam("username") String username)
	{
    	System.err.println(medications.getCode());
    	if(ePrescriptionService.findOneByCode(medications.getCode()) != null)
    	{
    		return new ResponseEntity<>("Qr code has been used once already.", HttpStatus.FORBIDDEN); 
    	}
    	
    	Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
		Patient patient = patientService.findByUsername(username);
		Integer pointsForPatient = 0;
		
		EPrescription ePrescription = new EPrescription();
		ePrescription.setPharmacy(pharmacy);
		ePrescription.setPatientFirstName(patient.getFirstName());
		ePrescription.setPatientLastName(patient.getLastName());
		ePrescription.setPrescribedDate(new Date());
		ePrescription.setPatient(patient);
		ePrescription.setCode(medications.getCode());
		
		List<EPrescriptionItem> eItems = new ArrayList<EPrescriptionItem>();
		Double totalPrice = 0.0;
		
		for (MedicationQrDTO med : medications.getMedications()) 
		{
			Medication medication = medicationService.findOne(med.getId());
			EPrescriptionItem item = new EPrescriptionItem(med.getQuantity(), medication, ePrescription);
			eItems.add(item);
			
			// Sabiranje poena za dodavanje pacijentu na kraju kupovine
			pointsForPatient += medication.getLoyaltyPoints() * med.getQuantity();
			
			PharmacyStorageItem psi = pharmacyStorageItemService.findOneWithMedicationAndPharmacy(medication.getId(), pharmacy.getId());
			psi = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			psi.setQuantity(psi.getQuantity() - med.getQuantity());
			pharmacyStorageItemService.save(psi);
			
			Double medicationPrice = pharmacyStorageItemService.getCurrentPrice(psi);
			Double finalMedicationPrice = loyaltyProgramService.getFinalPrice(medicationPrice, patient);
			totalPrice += finalMedicationPrice * med.getQuantity();
		}
		ePrescription.setPrescriptionItems(eItems);
		ePrescription.setPrice(totalPrice);
		ePrescriptionService.save(ePrescription);
		
		String message = loyaltyProgramService.generateMessage(patient, totalPrice, pointsForPatient);
		
		// Dodavanje poena pacijentu i izmena kategorije ako je potrebno
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		emailService.sendQrPickupConfirmation(patient);
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
		
}
