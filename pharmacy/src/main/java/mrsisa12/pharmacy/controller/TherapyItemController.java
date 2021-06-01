package mrsisa12.pharmacy.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.service.TherapyService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.TherapyItemService;

@RestController
@RequestMapping("/therapyItem")
public class TherapyItemController {

	@Autowired
	private TherapyItemService therapyItemService;
			
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private TherapyService therapyService;
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/addPrescription")
	public ResponseEntity<Void> savePrescriptionItem(@RequestParam String storageId,@RequestParam String quantity,@RequestParam String duration,
			 @RequestParam String pharmacyId, @RequestParam String ePrescriptionId) {

		if (storageId == "" || quantity == "" || duration=="") {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));
		Therapy epres = therapyService.findOne(Long.parseLong(ePrescriptionId));
		
		TherapyItem prescriptionItem = new TherapyItem();
		prescriptionItem.setEPrescription(epres);
		prescriptionItem.setMedication(pharmacyStorageItem.getMedication());
		prescriptionItem.setQuantity(Integer.parseInt(quantity));
		prescriptionItem.setTherapyDuration(Integer.parseInt(duration));
		prescriptionItem.setMedicationPrice(pharmacyStorageItem.getItemPrices().get(0).getPrice());
		therapyItemService.save(prescriptionItem);
		
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
