package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

import mrsisa12.pharmacy.dto.PharmacyWithMedicationPriceDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.service.ItemPriceService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
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
	private PatientService patientService;
	
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
	
	@GetMapping(value = "/fromPharmacyToPromote/{id}")
	public ResponseEntity<List<PharmacyStorageItemWithItemPricesDTO>> getAllMedicationsToPromote(@PathVariable Long id) {

		List<Medication> medications = medicationService.findAll();

		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(id);
		// convert medications to DTOs
		List<PharmacyStorageItemWithItemPricesDTO> medicationsDTO = new ArrayList<PharmacyStorageItemWithItemPricesDTO>();
		boolean found = false;
		for (PharmacyStorageItem psi : pharmacy.getPharmacyStorageItems()) {
			found = false;
			PharmacyStorageItem psiWithItemPrices = pharmacyStorageItemService.findOneWithItemPrices(psi.getId());
			for (Medication medication : medications) {
				if(psiWithItemPrices.getMedication().getId() == medication.getId()) {
					for (ItemPrice itemPrice : psiWithItemPrices.getItemPrices()) {
						if(itemPrice.isCurrent() && itemPrice.isPromotion()) {
							found = true;
							break;
						}
					}
				}
			}
			if(!found)
				medicationsDTO.add(new PharmacyStorageItemWithItemPricesDTO(psiWithItemPrices));
		}

		return new ResponseEntity<>(medicationsDTO, HttpStatus.OK);
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
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, false, timePeriod);
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
		pharmacyStorageItem.setCounter(0);
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
				ip.setPromotion(false);
				ip.getTimePeriod().setEndDate(localDateNow);
				ip.getTimePeriod().setEndTime(localTimeNow);
				itemPriceService.save(ip);
			}
		}
		// kreiramo trenutni datum i vrijeme za datum i vrijeme kreiranja, datum i vrijeme isteka je null
		TimePeriod timePeriod = new TimePeriod(localDateNow, localTimeNow, null, null);
		// pravim novi ItemPrice koji ce biti trenutna cijena
		ItemPrice itemPrice = new ItemPrice(pharmacyStorageItemWIPDTO.getItemPrices().get(0).getPrice(), true, false, timePeriod);
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
	
	//@PreAuthorize("hasAnyRole('PATIENT', 'GUEST')")
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
	
	@GetMapping(value = "/checkAvailableQuantity")
	public ResponseEntity<Integer> checkAvailableQuantity(@RequestParam String storageId, @RequestParam String pharmacyId) {
		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(Long.parseLong(storageId));

		int quantity = pharmacyStorageItem.getQuantity();
		if(quantity == 0) {
			Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));
			pharmacyStorageItem.incrementCounter();
			pharmacyStorageItemService.save(pharmacyStorageItem);
			
			String emailBody = "This email is an alert that the quantity of medication " + pharmacyStorageItem.getMedication().getName() + " in storage item #" + pharmacyStorageItem.getId() + " has become 0.";
			EmailContent email = new EmailContent("Storage item low quantity alert", emailBody);
			for (PharmacyAdmin padmin : pharmacy.getPharmacyAdmins()) {
				email.addRecipient(padmin.getEmail());
			}
	        emailService.sendEmail(email);  
		}
		return new ResponseEntity<>(quantity, HttpStatus.OK);
	}

	@GetMapping(value = "/getPharmacyStorage")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getPharmacyStorage(@RequestParam String pharmacyId, @RequestParam String patientUsername) {
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));
		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);

		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<>();
		for (PharmacyStorageItem e : pharmacy.getPharmacyStorageItems()) {
			boolean bad = false;
			for(Medication med : patient.getAllergies()) {
				if(e.getMedication().getId() == med.getId()) {
					bad = true;
					break;
				}
			}
			if(!bad) {
				pharmacyStorageItemsDTO.add(new PharmacyStorageItemDTO(e));
			}
		}
		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/getAlternatives")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getAlternatives(@RequestParam String pharmacyId, @RequestParam String medicationId, @RequestParam String patientUsername) {
		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(Long.parseLong(pharmacyId));
		Patient patient = patientService.findByUsernameWithAllergies(patientUsername);
		
		List<PharmacyStorageItemDTO> pharmacyStorageItemDTO = new ArrayList<>();
		List<Long> alts = new ArrayList<>();
		Medication medication = medicationService.findOneWithAlternatives(Long.parseLong(medicationId));
		if(medication != null)  {
			for (Medication med : medication.getAlternatives()) {
				boolean bad = false;
				for(Medication m : patient.getAllergies()) {
					if(med.getId() == m.getId()) {
						bad = true;
						break;
					}
				}
				if(!bad) {
					alts.add(med.getId());
				}
			} 
			for (PharmacyStorageItem e : pharmacy.getPharmacyStorageItems()) {
				if(alts.contains(e.getMedication().getId())) {
					pharmacyStorageItemDTO.add(new PharmacyStorageItemDTO(e));
				}
			}
		}
		
		return new ResponseEntity<>(pharmacyStorageItemDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getPharmacyStorageItemsOutOfStock/{id}")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getPharmacyStorageItemsOutOfStock(@PathVariable Long id) {

		List<PharmacyStorageItem> pharmacyStorageItems = pharmacyStorageItemService.findAllOutOfStock(id);
		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<PharmacyStorageItemDTO>();
		
		for (PharmacyStorageItem pharmacyStorageItem : pharmacyStorageItems) {
			pharmacyStorageItemsDTO.add(new PharmacyStorageItemDTO(pharmacyStorageItem));
		}
		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}
	
}
