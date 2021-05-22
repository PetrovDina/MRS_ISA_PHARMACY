	package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PharmacyAdminDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.PharmacyAdminService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.RoleService;

@RestController
@RequestMapping("/pharmacyAdmin")
public class PharmacyAdminController 
{
	@Autowired
	private PharmacyAdminService pharmacyAdminService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	EmailService emailService;
	
	@GetMapping(value = "/{username}")
	public ResponseEntity<PharmacyAdminDTO> getPharmacyAdminByUsername(@PathVariable String username) {

		PharmacyAdmin pharmacyAdmin = pharmacyAdminService.findOneByUsername(username);

		// pharmacyAdmin must exist
		if (pharmacyAdmin == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PharmacyAdminDTO(pharmacyAdmin), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<UserDTO>> getAllPharmacyAdmins() {

		List<PharmacyAdmin> patients = pharmacyAdminService.findAll();

		List<UserDTO> pharmacyAdminsDTO = new ArrayList<>();
		for (PharmacyAdmin p : patients) {
			pharmacyAdminsDTO.add(new UserDTO(p));
		}

		return new ResponseEntity<>(pharmacyAdminsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<PharmacyAdminDTO> savePharmacyAdmin(@RequestBody PharmacyAdminDTO pharmacyAdminDTO)
	{
		PharmacyAdmin pa = new PharmacyAdmin();
		pa.setUsername(pharmacyAdminDTO.getUsername());
		pa.setPassword(passwordEncoder.encode(pharmacyAdminDTO.getPassword()));
		pa.setEmail(pharmacyAdminDTO.getEmail());
		pa.setFirstName(pharmacyAdminDTO.getFirstName());
		pa.setLastName(pharmacyAdminDTO.getLastName());
		pa.setLocation(pharmacyAdminDTO.getLocation());
		pa.setGender(pharmacyAdminDTO.getGender());
		pa.setActiveStatus(UserStatus.UNVERIFIED);
		pa.setRoles(roleService.findByName("ROLE_PHARMACY_ADMIN"));
		pa.setDeleted(false);
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyAdminDTO.getPharmacyId());
		pa.setPharmacy(pharmacy);
		
		try 
		{
			pharmacyAdminService.save(pa);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
	
		emailService.confirmationEmailUserRegistration(pa);
		
		return new ResponseEntity<>(new PharmacyAdminDTO(pa), HttpStatus.CREATED);
	}
}
