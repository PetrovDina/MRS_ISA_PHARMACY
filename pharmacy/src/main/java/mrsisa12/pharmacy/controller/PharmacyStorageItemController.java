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
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
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
	
	/*@PostMapping(consumes = "application/json")
	public ResponseEntity<PharmacyStorageItemDTO> createPharmacyStorageItem(@RequestBody PharmacyStorageItemDTO pharmacyStorageItemDTO) {

		if (pharmacyStorageItemDTO.getMedicationId() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(pharmacyStorageItemDTO.getPharmacyId());
		//Medication medication = medicationService.findOneWithStorageItems(pharmacyStorageItemDTO.getMedication().getId()); TODO
		
		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		PharmacyStorageItem pharmacyStorageItem = new PharmacyStorageItem();
		pharmacyStorageItem.setQuantity(pharmacyStorageItemDTO.getQuantity());
		//pharmacyStorageItem.setPharmacy(pharmacy);
		//pharmacyStorageItem.setMedication(medication);
		pharmacy.addPharmacyStorageItem(pharmacyStorageItem);

		pharmacyStorageItem = pharmacyStorageItemService.save(pharmacyStorageItem);
		return new ResponseEntity<>(new PharmacyStorageItemDTO(pharmacyStorageItem), HttpStatus.CREATED);
	}*/
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<PharmacyStorageItemDTO> updatePharmacyStorageItem(@RequestBody PharmacyStorageItemDTO pharmacyStorageItemDTO) {

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(pharmacyStorageItemDTO.getId());
		
		if (pharmacyStorageItem == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		pharmacyStorageItem.setQuantity(pharmacyStorageItemDTO.getQuantity());
		
		pharmacyStorageItem = pharmacyStorageItemService.save(pharmacyStorageItem);
		return new ResponseEntity<>(new PharmacyStorageItemDTO(pharmacyStorageItem), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePharmacyStorageItem(@PathVariable Long id) {

		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(id);

		if (pharmacyStorageItem != null) {
			pharmacyStorageItemService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
