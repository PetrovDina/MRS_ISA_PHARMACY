package mrsisa12.pharmacy.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.MedicationQrDTO;
import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.EPrescriptionItem;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.repository.EPrescriptionRepository;

@Service
public class EPrescriptionService {
	
	@Autowired
	private EPrescriptionRepository ePrescriptionRepository;
	
	
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
	
	

	public EPrescription save(EPrescription ePrescription) 
	{
		return ePrescriptionRepository.save(ePrescription);
	}

	public List<EPrescription> findAllByPatientWithPrescriptionItems(String patientUsername) 
	{
		return ePrescriptionRepository.findAllByPatientWithPrescriptionItems(patientUsername); 
	}
	
	public EPrescription findOneByCode(String code)
	{
		return ePrescriptionRepository.findOneByCode(code);
	}
	
	public List<EPrescription>  findAllByPatient(Patient patient)
	{
		return ePrescriptionRepository.findAllByPatient(patient);
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String buyMedicationsByQrSearch( QrCodeDTO medications, Long pharmacyId, String username)
	{
    	if(this.findOneByCode(medications.getCode()) != null)
    	{
    		return "Qr code has been used once already.";
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
			
			//pessimistic write
			PharmacyStorageItem psi = pharmacyStorageItemService.findOneWithMedicationAndPharmacy(medication.getId(), pharmacy.getId());
			psi = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			
			if (psi.getQuantity() < med.getQuantity()) 
			{
				throw new IllegalArgumentException();
			}
			
			psi.setQuantity(psi.getQuantity() - med.getQuantity());
			pharmacyStorageItemService.save(psi);
			
			Double medicationPrice = pharmacyStorageItemService.getCurrentPrice(psi);
			Double finalMedicationPrice = loyaltyProgramService.getFinalPrice(medicationPrice, patient);
			totalPrice += finalMedicationPrice * med.getQuantity();
		}
		ePrescription.setPrescriptionItems(eItems);
		ePrescription.setPrice(totalPrice);
		
		this.save(ePrescription);
		
		String message = loyaltyProgramService.generateMessage(patient, totalPrice, pointsForPatient);
		
		// Dodavanje poena pacijentu i izmena kategorije ako je potrebno
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		emailService.sendQrPickupConfirmation(patient);
		
		return message;
	}
}
