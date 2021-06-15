package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.service.TherapyItemService;

@RestController
@RequestMapping("/therapyItem")
public class TherapyItemController {

	@Autowired
	private TherapyItemService therapyItemService;			
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/addPrescription")
	public ResponseEntity<String> savePrescriptionItem(@RequestParam String storageId,@RequestParam String quantity,@RequestParam String duration,
	 @RequestParam String pharmacyId, @RequestParam String ePrescriptionId) {
		
		try {
			therapyItemService.savePrescriptionItem(storageId, quantity, duration, pharmacyId, ePrescriptionId);
			return new ResponseEntity<>("ok", HttpStatus.CREATED);


		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>("Unfortunately, we don't have enough medicine in storage. Please try again later.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
		
}
