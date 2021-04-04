package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ReservationItemDTO;
import mrsisa12.pharmacy.model.ReservationItem;
import mrsisa12.pharmacy.service.ReservationItemService;

@RestController
@RequestMapping("/reservationItem")
public class ReservationItemController {

	@Autowired
	private ReservationItemService reservationItemService;
	
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ReservationItemDTO>> getAllReservationItems() {

		List<ReservationItem> reservationItems = reservationItemService.findAll();

		List<ReservationItemDTO> reservationItemDTOs = new ArrayList<>();
		for (ReservationItem m : reservationItems) {
			reservationItemDTOs.add(new ReservationItemDTO(m));
		}

		return new ResponseEntity<>(reservationItemDTOs, HttpStatus.OK);
	}
	
	@GetMapping //zar ne treba ovde neki path ? TODO
	public ResponseEntity<List<ReservationItemDTO>> getAllReservationItemsPage(Pageable page) {

		Page<ReservationItem> reservationItems = reservationItemService.findAll(page);

		List<ReservationItemDTO> reservationItemDTOs = new ArrayList<>();
		for (ReservationItem m : reservationItems) {
			reservationItemDTOs.add(new ReservationItemDTO(m));
		}

		return new ResponseEntity<>(reservationItemDTOs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ReservationItemDTO> getReservationItem(@PathVariable Long id) {

		ReservationItem reservationItem = reservationItemService.findOne(id);

		if (reservationItem == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ReservationItemDTO(reservationItem), HttpStatus.OK);
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
	
//	@PutMapping(consumes = "application/json")
//	public ResponseEntity<PharmacyStorageItemDTO> updatePharmacyStorageItem(@RequestBody PharmacyStorageItemDTO pharmacyStorageItemDTO) {
//
//		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(pharmacyStorageItemDTO.getId());
//		
//		if (pharmacyStorageItem == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		pharmacyStorageItem.setQuantity(pharmacyStorageItemDTO.getQuantity());
//		
//		pharmacyStorageItem = pharmacyStorageItemService.save(pharmacyStorageItem);
//		return new ResponseEntity<>(new PharmacyStorageItemDTO(pharmacyStorageItem), HttpStatus.OK);
//	}
//	
//	@DeleteMapping(value = "/{id}")
//	public ResponseEntity<Void> deletePharmacyStorageItem(@PathVariable Long id) {
//
//		PharmacyStorageItem pharmacyStorageItem = pharmacyStorageItemService.findOne(id);
//
//		if (pharmacyStorageItem != null) {
//			pharmacyStorageItemService.remove(id);
//			return new ResponseEntity<>(HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//		}
//	}
}
