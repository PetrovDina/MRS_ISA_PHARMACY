package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PharmacistDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.PharmacistService;
import mrsisa12.pharmacy.service.RoleService;

@RestController
@RequestMapping("/pharmacist")
public class PharmacistController {

	@Autowired
	PharmacistService pharmacistService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	EmailService emailService;

	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<PharmacistDTO> savePharmacist(@RequestBody UserDTO pharmacistDTO) {
		Pharmacist phar = new Pharmacist();
		phar.setUsername(pharmacistDTO.getUsername());
		phar.setPassword(passwordEncoder.encode(pharmacistDTO.getPassword()));
		phar.setEmail(pharmacistDTO.getEmail());
		phar.setFirstName(pharmacistDTO.getFirstName());
		phar.setLastName(pharmacistDTO.getLastName());
		phar.setLocation(pharmacistDTO.getLocation());
		phar.setGender(pharmacistDTO.getGender());
		phar.setActiveStatus(UserStatus.UNVERIFIED);
		phar.setRoles(roleService.findByName("ROLE_PHARMACIST"));
		phar.setRating(0.0);
		phar.setDeleted(false);

		pharmacistService.save(phar);
		emailService.confirmationEmailUserRegistration(phar);
		
		return new ResponseEntity<>(new PharmacistDTO(phar), HttpStatus.CREATED);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<PharmacistDTO>> getAllPharmacists() {

		List<Pharmacist> pharmacists = pharmacistService.findAll();

		// convert dermatologists to DTOs
		List<PharmacistDTO> pharmacistsDTOs = new ArrayList<>();
		for (Pharmacist pharmacist : pharmacists) {
			pharmacistsDTOs.add(new PharmacistDTO(pharmacist));
		}

		return new ResponseEntity<>(pharmacistsDTOs, HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

		Pharmacist pharmacist = pharmacistService.findOne(id);

		if (pharmacist != null) {
			pharmacistService.deletePharmacist(pharmacist);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
