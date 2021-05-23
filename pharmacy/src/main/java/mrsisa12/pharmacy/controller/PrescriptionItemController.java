package mrsisa12.pharmacy.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PrescriptionItemDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.PrescriptionItem;
import mrsisa12.pharmacy.model.enums.EPrescriptionStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EPrescriptionService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.PrescriptionItemService;

@RestController
@RequestMapping("/prescriptionItem")
public class PrescriptionItemController {

	@Autowired
	private PrescriptionItemService prescriptionItemService;
			
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private EPrescriptionService ePrescriptionService;
	
	@GetMapping(value = "/addPrescription")
	public ResponseEntity<Void> savePrescriptionItem(@RequestParam String storageId,@RequestParam String quantity,@RequestParam String duration,
			 @RequestParam String pharmacyId, @RequestParam String ePrescriptionId) {

		if (storageId == "" || quantity == "" || duration=="") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));
		EPrescription epres = ePrescriptionService.findOne(Long.parseLong(ePrescriptionId));
		
		PrescriptionItem prescriptionItem = new PrescriptionItem();
		prescriptionItem.setEPrescription(epres);
		prescriptionItem.setMedication(pharmacyStorageItem.getMedication());
		prescriptionItem.setQuantity(Integer.parseInt(quantity));
		prescriptionItem.setTherapyDuration(Integer.parseInt(duration));
		prescriptionItemService.save(prescriptionItem);
		
		pharmacyStorageItem.setQuantity(pharmacyStorageItem.getQuantity() - Integer.parseInt(quantity));
		pharmacyStorageItem = pharmacyStorageItemService.save(pharmacyStorageItem);
		
		if(pharmacyStorageItem.getQuantity() == 0) {
			Pharmacy pharmacy = pharmacyService.findOneWithPharmacyAdmins(Long.parseLong(pharmacyId));
			pharmacyStorageItem.incrementCounter();
			pharmacyStorageItemService.save(pharmacyStorageItem);
			
			String emailBody = "This email is an alert that the quantity of medication " + pharmacyStorageItem.getMedication().getName() + " in storage item #" + pharmacyStorageItem.getId() + " has become 0.";
			EmailContent email = new EmailContent("Storage item low quantity alert", emailBody);
			for (PharmacyAdmin padmin : pharmacy.getPharmacyAdmins()) {
				email.addRecipient(padmin.getEmail());
			}
	        emailService.sendEmail(email);  
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
}
