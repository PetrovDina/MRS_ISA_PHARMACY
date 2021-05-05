package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PharmacyAdminDTO;
import mrsisa12.pharmacy.dto.UserDTO;
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
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<UserDTO>> getAllPharmacyAdmins() {

		List<PharmacyAdmin> patients = pharmacyAdminService.findAll();

		List<UserDTO> pharmacyAdminsDTO = new ArrayList<>();
		for (PharmacyAdmin p : patients) {
			pharmacyAdminsDTO.add(new UserDTO(p));
		}

		return new ResponseEntity<>(pharmacyAdminsDTO, HttpStatus.OK);
	}
	
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
		
		pharmacyAdminService.save(pa);
		return new ResponseEntity<>(new PharmacyAdminDTO(pa), HttpStatus.CREATED);
	}
}
