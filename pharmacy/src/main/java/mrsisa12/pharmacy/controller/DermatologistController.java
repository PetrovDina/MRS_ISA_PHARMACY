package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.DermatologistDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.DermatologistService;
import mrsisa12.pharmacy.service.RoleService;

@RestController
@RequestMapping("/dermatologist")
public class DermatologistController {

	@Autowired
	private DermatologistService dermatologistService;		
	
	@Autowired
	private PasswordEncoder passwordEncoder;		
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	EmailService emailService;
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDTO> saveSystemAdmin(@RequestBody UserDTO dermatologistDTO)
	{
		Dermatologist d = new Dermatologist();
		d.setUsername(dermatologistDTO.getUsername());
		d.setPassword(passwordEncoder.encode(dermatologistDTO.getPassword()));
		d.setEmail(dermatologistDTO.getEmail());
		d.setFirstName(dermatologistDTO.getFirstName());
		d.setLastName(dermatologistDTO.getLastName());
		d.setLocation(dermatologistDTO.getLocation());
		d.setGender(dermatologistDTO.getGender());
		d.setActiveStatus(UserStatus.UNVERIFIED);
		d.setRoles(roleService.findByName("ROLE_DERMATOLOGIST"));
		d.setRating(0.0);
		d.setDermatologistNickname(null);
		d.setDeleted(false);
		d.setLoggedFirstTime(false);
		
		try 
		{
			dermatologistService.save(d);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}

		emailService.confirmationEmailUserRegistration(d);
		
		return new ResponseEntity<>(new UserDTO(d), HttpStatus.CREATED);
	}

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
