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

@RestController
@RequestMapping("/loyalty")
public class LoyaltyProgramController {
	
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN') or hasRole('PATIENT')")
	@GetMapping(value = "/getLoyalty")
	public ResponseEntity<LoyaltyProgramDTO> getAllMedications() {

		LoyaltyProgram loyaltyProgram = loyaltyProgramService.getLoyaltyProgram();

		return new ResponseEntity<>(new LoyaltyProgramDTO(loyaltyProgram), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PutMapping(value = "/updateLoyalty", consumes = "application/json")
	public ResponseEntity<LoyaltyProgramDTO> updateMedication(@RequestBody LoyaltyProgramDTO loyaltyProgramDTO) {
		try 
		{
			LoyaltyProgram loyaltyProgram = loyaltyProgramService.updateLoyaltyProgram(loyaltyProgramDTO);
			
			return new ResponseEntity<>(new LoyaltyProgramDTO(loyaltyProgram), HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}

}
