package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.repository.TherapyItemRepository;

@Service
public class TherapyItemService {

	@Autowired
	private TherapyItemRepository prescriptionItemRepository;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private TherapyService therapyService;
	
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@Autowired
	private PatientService patientService;
	
	public TherapyItem save(TherapyItem prescriptionItem) {
		return prescriptionItemRepository.save(prescriptionItem);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public void savePrescriptionItem( String storageId, String quantity, String duration,
			  String pharmacyId,  String ePrescriptionId)throws IllegalArgumentException
	{
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));
		Therapy epres = therapyService.findOneWithPatient(Long.parseLong(ePrescriptionId));
		
		//provera da li je broj na stanju manji od trazenog i baciti gresku ako jeste *dodato zbog konkurentnosti*
		
		if (pharmacyStorageItem.getQuantity() < Integer.parseInt(quantity)) {
			throw new IllegalArgumentException();
		}
		
		TherapyItem prescriptionItem = new TherapyItem();
		prescriptionItem.setEPrescription(epres);
		prescriptionItem.setMedication(pharmacyStorageItem.getMedication());
		prescriptionItem.setQuantity(Integer.parseInt(quantity));
		prescriptionItem.setTherapyDuration(Integer.parseInt(duration));
		
		double finalPrice = loyaltyProgramService.getFinalPrice(pharmacyStorageItem.getItemPrices().get(0).getPrice(), epres.getPatient()) * Integer.parseInt(quantity);
		prescriptionItem.setMedicationPrice(finalPrice);
		prescriptionItemRepository.save(prescriptionItem);
		
		Integer pointsForPatient = pharmacyStorageItem.getMedication().getLoyaltyPoints() * Integer.parseInt(quantity);
		patientService.addPointsAndUpdateCategory(epres.getPatient(), pointsForPatient);
		
		pharmacyStorageItem.setQuantity(pharmacyStorageItem.getQuantity() - Integer.parseInt(quantity));
		pharmacyStorageItem = pharmacyStorageItemService.save(pharmacyStorageItem);
		
		if(pharmacyStorageItem.getQuantity() == 0) {
			Pharmacy pharmacy = pharmacyService.findOneWithPharmacyAdmins(Long.parseLong(pharmacyId));
			pharmacyStorageItem.incrementCounter();
			pharmacyStorageItemService.save(pharmacyStorageItem);
			
			String emailBody = "This email is an alert that the quantity of medication " + pharmacyStorageItem.getMedication().getName() + " in storage item #" + pharmacyStorageItem.getId() + " has become 0.";
			EmailContent email = new EmailContent("Storage item low quantity alert!", emailBody);
			for (PharmacyAdmin padmin : pharmacy.getPharmacyAdmins()) {
				email.addRecipient(padmin.getEmail());
			}
	        emailService.sendEmail(email);  
		}
		
	}
}
