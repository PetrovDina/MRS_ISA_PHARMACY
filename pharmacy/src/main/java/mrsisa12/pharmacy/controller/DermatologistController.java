package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.DermatologistDTO;
import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.service.DermatologistService;

@RestController
@RequestMapping("/dermatologists")
public class DermatologistController {
	
	@Autowired
	private DermatologistService dermatologistService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<DermatologistDTO>> getAllDermatologists() {

		List<Dermatologist> dermatologists = dermatologistService.findAll();

		// convert dermatologists to DTOs
		List<DermatologistDTO> dermatologistsDTO = new ArrayList<>();
		for (Dermatologist dermatologist : dermatologists) {
			dermatologistsDTO.add(new DermatologistDTO(dermatologist));
		}

		return new ResponseEntity<>(dermatologistsDTO, HttpStatus.OK);
	}
}
