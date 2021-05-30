package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.LoyaltyProgramDTO;
import mrsisa12.pharmacy.model.LoyaltyProgram;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.PatientService;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyProgramController {
	
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@Autowired
	private PatientService patientService;
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@GetMapping(value = "/getLoyalty")
	public ResponseEntity<LoyaltyProgramDTO> getAllMedications() {

		LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgram();

		return new ResponseEntity<>(new LoyaltyProgramDTO(loyaltyProgram), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PutMapping(value = "/updateLoyalty", consumes = "application/json")
	public ResponseEntity<LoyaltyProgramDTO> updateMedication(@RequestBody LoyaltyProgramDTO loyaltyProgramDTO) {

		LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgram();
		
		loyaltyProgram.setAfterAppointment(loyaltyProgramDTO.getAfterAppointment());
		loyaltyProgram.setMaxPointsRegular(loyaltyProgramDTO.getMaxPointsRegular());
		loyaltyProgram.setMaxPointsSilver(loyaltyProgramDTO.getMaxPointsSilver());
		loyaltyProgram.setSilverDis(loyaltyProgramDTO.getSilverDis());
		loyaltyProgram.setGoldDis(loyaltyProgramDTO.getGoldDis());
		
		loyaltyProgramService.save(loyaltyProgram);
		patientService.updateCategories(loyaltyProgram.getMaxPointsRegular(), loyaltyProgram.getMaxPointsSilver());
		
		return new ResponseEntity<>(new LoyaltyProgramDTO(loyaltyProgram), HttpStatus.OK);
	}

}
