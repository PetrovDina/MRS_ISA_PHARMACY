package mrsisa12.pharmacy.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
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
import mrsisa12.pharmacy.dto.PharmacyWithMedicationPriceDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.service.ItemPriceService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;

@RestController
@RequestMapping("/pharmacyStorageItem")
public class PharmacyStorageItemController {

	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;

	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private MedicationService medicationService;

	@Autowired
	private ItemPriceService itemPriceService;
	
	@Autowired
	private EmailService emailService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getAllPharmacyStorageItems() {

		List<PharmacyStorageItem> pharmacyStorageItems = pharmacyStorageItemService.findAll();

		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<>();
		for (PharmacyStorageItem m : pharmacyStorageItems) {
			pharmacyStorageItemsDTO.add(new PharmacyStorageItemDTO(m));
		}

		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PharmacyStorageItemDTO>> getPharmacyStorageItemsPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<PharmacyStorageItem> pharmacyStorageItems = pharmacyStorageItemService.findAll(page);

		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<>();
		for (PharmacyStorageItem m : pharmacyStorageItems) {
			pharmacyStorageItemsDTO.add(new PharmacyStorageItemDTO(m));
		}

		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PharmacyStorageItemDTO> getPharmacyStorageItem(@PathVariable Long id) {

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(id);

		if (pharmacyStorageItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PharmacyStorageItemDTO(pharmacyStorageItem), HttpStatus.OK);
	}

	@GetMapping(value = "/fromPharmacy/{id}")
	public ResponseEntity<List<PharmacyStorageItemWithItemPricesDTO>> getPharmacyStorageItemsFromPharmacyById(
			@PathVariable Long id) {
		// dovlacim apoteku sa svim pharmacyStorageItem-ima
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(id);
		// kreiram listu koju cu napuniti i vratiti
		List<PharmacyStorageItemWithItemPricesDTO> pharmacyStorageItemDTOs = new ArrayList<PharmacyStorageItemWithItemPricesDTO>();
		for (PharmacyStorageItem psi : pharmacy.getPharmacyStorageItems()) {
			// za pharmacyStorageItem trazim sve cijene
			PharmacyStorageItem psi_test = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			psi.setItemPrices(psi_test.getItemPrices());
			// za pharmacyStorageItem trazim Medication za koji je vezan
			PharmacyStorageItem psi_test2 = pharmacyStorageItemService.findOneWithMedication(psi.getId());
			psi.setMedication(psi_test2.getMedication());
			// ubacujemo i apoteku
			psi.setPharmacy(pharmacy);
			pharmacyStorageItemDTOs.add(new PharmacyStorageItemWithItemPricesDTO(psi));
		}

		return new ResponseEntity<>(pharmacyStorageItemDTOs, HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<PharmacyStorageItemDTO> createPharmacyStorageItem(
			@RequestBody PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO) {

		if (pharmacyStorageItemWIPDTO == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(pharmacyStorageItemWIPDTO.getPharmacy().getId());
		Medication medication = medicationService.findOne(pharmacyStorageItemWIPDTO.getMedication().getId());
		// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
		LocalDate localDateNow = LocalDate.now();
		LocalTime localTimeNow = LocalTime.now();
		TimePeriod timePeriod = new TimePeriod(localDateNow, localTimeNow, null, null);
		// potrebno je kreirati novu cijenu
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, timePeriod);
		PharmacyStorageItem pharmacyStorageItem = new PharmacyStorageItem();
		// postavljamo pharmacyStorageItem itemPrice-u
		itemPrice.setPharmacyStorageItem(pharmacyStorageItem);
		// dodajemo cijenu
		pharmacyStorageItem.addItemPrice(itemPrice);
		// dodajemo pokazivac na lijek
		pharmacyStorageItem.setMedication(medication);
		// dodajemo kolicinu
		pharmacyStorageItem.setQuantity(pharmacyStorageItemWIPDTO.getQuantity());
		// postoje je kreiranje onda je deleted na false
		pharmacyStorageItem.setDeleted(false);
		// dodajemo apotkue novom pharmacyStorageItem-u
		pharmacyStorageItem.setPharmacy(pharmacy);
		pharmacyStorageItemService.save(pharmacyStorageItem);
		return new ResponseEntity<>(
				new PharmacyStorageItemDTO(pharmacyStorageItemService.findOne(pharmacyStorageItem.getId())),
				HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<PharmacyStorageItemDTO> updatePharmacyStorageItem(
			@RequestBody PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO) {

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService
				.findOneWithItemPrices(pharmacyStorageItemWIPDTO.getId());
		if (pharmacyStorageItem == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// postavlja se nova kolicina
		// pharmacyStorageItem.setQuantity(pharmacyStorageItemDTO.getQuantity());
		LocalDate localDateNow = LocalDate.now();
		LocalTime localTimeNow = LocalTime.now();
		// ovdje onu sto je true postavljam na false
		for (ItemPrice ip : pharmacyStorageItem.getItemPrices()) {
			if (ip.isCurrent()) {
				ip.setCurrent(false);
				ip.getTimePeriod().setEndDate(localDateNow);
				ip.getTimePeriod().setEndTime(localTimeNow);
			}
		}
		// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
		TimePeriod timePeriod = new TimePeriod(localDateNow, localTimeNow, null, null);
		// pravim novi ItemPrice koji ce biti trenutna cijena
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, timePeriod);
		// dodajemo itemPrice-u referencu na pharmacyStorageItem
		itemPrice.setPharmacyStorageItem(pharmacyStorageItem);
		// cuvamo itemPrice
		itemPriceService.save(itemPrice);
		
		pharmacyStorageItem.addItemPrice(itemPrice); // vracam update-ovan lijek - odnosno lijek koji sada ima novu cijenu
		
		return new ResponseEntity<>(new PharmacyStorageItemWithItemPricesDTO(pharmacyStorageItem), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePharmacyStorageItem(@PathVariable Long id) {

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(id);

		if (pharmacyStorageItem != null) {
			pharmacyStorageItemService.deletePharmacyStorageItem(pharmacyStorageItem);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/allByMedicationAndQuantity")
	public ResponseEntity<List<PharmacyWithMedicationPriceDTO>> findAllWithItemMedication(@RequestParam Long medicationId) {

		List<PharmacyStorageItem> results = pharmacyStorageItemService.findAllWithCurrentPriceByMedication(medicationId);

		List<PharmacyWithMedicationPriceDTO> dtoList = new ArrayList<PharmacyWithMedicationPriceDTO>();
		
		for (PharmacyStorageItem item : results) {
			Pharmacy pharmacy = pharmacyService.findOneByStorageItem(item.getId());
			PharmacyWithMedicationPriceDTO dto = new PharmacyWithMedicationPriceDTO();
			
			dto.setPharmacy(new PharmacyDTO(pharmacy));
			dto.setAvailableQuantity(item.getQuantity());
			dto.setCurrentPrice(item.getItemPrices().get(0).getPrice());
			
			dtoList.add(dto);

			
		}

		return new ResponseEntity<>(dtoList, HttpStatus.OK);

	}
	
	private void sendNotification(String storageId, String pharmacyId) {
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));
		
		String emailBody = "This email is an alert that the quantity of medication " + pharmacyStorageItem.getMedication().getName() + " in storage item #" + pharmacyStorageItem.getId() + " has become 0.";
		EmailContent email = new EmailContent("Storage item low quantity alert", emailBody);
		for (PharmacyAdmin padmin : pharmacy.getPharmacyAdmins()) {
			email.addRecipient(padmin.getEmail());
		}
        emailService.sendEmail(email);        
	}
	
	@GetMapping(value = "/checkAvailableQuantity")
	public ResponseEntity<Integer> checkAvailableQuantity(@RequestParam String storageId, @RequestParam String pharmacyId) {
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));

		int quantity = pharmacyStorageItem.getQuantity();
		if(quantity == 0) {
			sendNotification(storageId, pharmacyId);
		}
		return new ResponseEntity<>(quantity, HttpStatus.OK);
	}

	@GetMapping(value = "/getPharmacyStorage")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getPharmacyStorage(@RequestParam String pharmacyId) {
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));

		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<>();
		for (PharmacyStorageItem e : pharmacy.getPharmacyStorageItems()) {
			pharmacyStorageItemsDTO.add(new PharmacyStorageItemDTO(e));
		}
		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAlternatives")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getAlternatives(@RequestParam String pharmacyId, @RequestParam String medicationId, @RequestParam String patientUsername) {
		//TODO allergies
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));
		//Patient patient = patientService.findByUsername(patientUsername);
		
		List<PharmacyStorageItemDTO> pharmacyStorageItemDTO = new ArrayList<>();
		List<Long> alts = new ArrayList<>();
		Medication medication = medicationService.findOneWithAlternatives(Long.parseLong(medicationId));
		if(medication != null)  {
			for (Medication med : medication.getAlternatives()) {
				alts.add(med.getId());
			} 
			for (PharmacyStorageItem e : pharmacy.getPharmacyStorageItems()) {
				if(alts.contains(e.getMedication().getId())) {
					pharmacyStorageItemDTO.add(new PharmacyStorageItemDTO(e));
				}
			}
		}
		
		return new ResponseEntity<>(pharmacyStorageItemDTO, HttpStatus.OK);
	}
	
	
	
}
