package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.RoleService;
import mrsisa12.pharmacy.service.SystemAdminService;

@RestController
@RequestMapping("/systemAdmin")
public class SystemAdminController {
	
	@Autowired
	private SystemAdminService systemAdminService;		
	
	@Autowired
	private PasswordEncoder passwordEncoder;		
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	EmailService emailService;
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDTO> saveSystemAdmin(@RequestBody UserDTO systemAdminDTO)
	{
		SystemAdmin sa = new SystemAdmin();
		sa.setUsername(systemAdminDTO.getUsername());
		sa.setPassword(passwordEncoder.encode(systemAdminDTO.getPassword()));
		sa.setEmail(systemAdminDTO.getEmail());
		sa.setFirstName(systemAdminDTO.getFirstName());
		sa.setLastName(systemAdminDTO.getLastName());
		sa.setLocation(systemAdminDTO.getLocation());
		sa.setGender(systemAdminDTO.getGender());
		sa.setActiveStatus(UserStatus.UNVERIFIED);
		sa.setRoles(roleService.findByName("ROLE_SYSTEM_ADMIN"));
		sa.setDeleted(false);
		
		systemAdminService.save(sa);
		emailService.confirmationEmailUserRegistration(sa);
		
		return new ResponseEntity<>(new UserDTO(sa), HttpStatus.CREATED);
	}

}
