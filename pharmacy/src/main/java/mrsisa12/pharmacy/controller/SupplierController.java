package mrsisa12.pharmacy.controller;

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

import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.RoleService;
import mrsisa12.pharmacy.service.SupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;		
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	EmailService emailService;
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<UserDTO> saveSupplier(@RequestBody UserDTO supplierDTO)
	{
		Supplier su = new Supplier();
		su.setUsername(supplierDTO.getUsername());
		su.setPassword(passwordEncoder.encode(supplierDTO.getPassword()));
		su.setEmail(supplierDTO.getEmail());
		su.setFirstName(supplierDTO.getFirstName());
		su.setLastName(supplierDTO.getLastName());
		su.setLocation(supplierDTO.getLocation());
		su.setGender(supplierDTO.getGender());
		su.setActiveStatus(UserStatus.UNVERIFIED);
		su.setRoles(roleService.findByName("ROLE_SUPPLIER"));
		su.setDeleted(false);
		
		try 
		{
			supplierService.save(su);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		
		emailService.confirmationEmailUserRegistration(su);
		
		return new ResponseEntity<>(new UserDTO(su), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('SUPPLIER')")
	@GetMapping(value = "/getId/{username}", consumes = "application/json")
	public ResponseEntity<Long> getSupplierId(@PathVariable String username)
	{
		Supplier supplier = supplierService.findOne(username);
		
		return new ResponseEntity<>(supplier.getId(), HttpStatus.OK);
	}
	

}
