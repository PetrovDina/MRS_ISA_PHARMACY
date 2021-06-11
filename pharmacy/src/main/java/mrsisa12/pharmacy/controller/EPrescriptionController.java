package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.dto.TherapyWithItemsDTO;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.service.EPrescriptionService;

@RestController
@RequestMapping("/ePrescription")
public class EPrescriptionController {
	
	@Autowired
	EPrescriptionService ePrescriptionService;
	
	@PreAuthorize("hasRole('PATIENT')")
    @PostMapping(value = "/buyMedicationsQr", consumes = "application/json")
	public ResponseEntity<String> buyMedicationsByQrSearch(@RequestBody QrCodeDTO medications, 
			@RequestParam("pharmacyId") Long pharmacyId, @RequestParam("username") String username)
	{
		try
		{
			String message = ePrescriptionService.buyMedicationsByQrSearch(medications, pharmacyId, username);
			if (message.equals("Qr code has been used once already.")) 
			{
	    		return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
	    	}
			else
			{
				return new ResponseEntity<>(message, HttpStatus.CREATED);
			}
		}
		catch(Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}
    
	@PreAuthorize("hasRole('PATIENT')")
    @GetMapping(value = "/byPatient")
	public ResponseEntity<List<TherapyWithItemsDTO>> getAllByPatient(@RequestParam String patientUsername) {

		List<EPrescription> prescriptions = ePrescriptionService.findAllByPatientWithPrescriptionItems(patientUsername);
		

		// convert to DTOs
		List<TherapyWithItemsDTO> prescriptionsDTO = new ArrayList<>();
		for (EPrescription prescription : prescriptions) {
			
			prescriptionsDTO.add(new TherapyWithItemsDTO(prescription));
		}

		return new ResponseEntity<>(prescriptionsDTO, HttpStatus.OK);
	}
		
}
