package mrsisa12.pharmacy.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.LoginDTO;
import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.dto.UserTokenStateDTO;
import mrsisa12.pharmacy.model.User;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.UserService;
import mrsisa12.pharmacy.utils.TokenUtils;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

	@Autowired
	private TokenUtils tokenUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private LocationService locationService;
	

	// Prvi endpoint koji pogadja korisnik kada se loguje.
	// Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
	@PostMapping("/login")
	public ResponseEntity<UserTokenStateDTO> createAuthenticationToken(
			@RequestBody LoginDTO loginRequest, HttpServletResponse response) {

		// Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
		// AuthenticationException
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginRequest.getUsername(), loginRequest.getPassword()));

		// Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
		// kontekst
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Kreiraj token za tog korisnika
		User user = (User) authentication.getPrincipal();
		// Potrebno srediti uloge - koje slati iz liste ili slati celu listu
		String jwt = tokenUtils.generateToken(user.getUsername(), user.getRoles().get(0).getName().substring(5));
		int expiresIn = tokenUtils.getExpiredIn();
		
		if(user.getActiveStatus() != UserStatus.ACTIVATED)
		{
			return new ResponseEntity<>(null, HttpStatus.LOCKED);
		}

		// Vrati token kao odgovor na uspesnu autentifikaciju
		return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
	}
	
	@GetMapping(value = "/usernameTaken/{username}")
	public ResponseEntity<Boolean> usernameTaken(@PathVariable("username") String username) {

		User user = userService.findByUsername(username);
		
		if (user == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@GetMapping(value = "/tryChangePassword")
	public ResponseEntity<Boolean> tryChangePassword(@RequestParam("username") String username, @RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {

		User user = userService.findByUsername(username);
		
		if (user == null) {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
		
		//provera sifre i postavljanje nove!
		try {
			Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					username, oldPassword));
			
			//ispravna lozinka
			user.setPassword(passwordEncoder.encode(newPassword));
			userService.save(user);
			
			
			
		}catch (AuthenticationException e) {
			return new ResponseEntity<>(false, HttpStatus.OK); //neispravna lozinka
		}
		

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@GetMapping(value = "/confirm-registration/{username}")
	public ResponseEntity<Object> confirmRegistration(@PathVariable("username") String username) {

		User user = userService.findByUsername(username);
		
		if (user == null) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		user.setActiveStatus(UserStatus.ACTIVATED);
		userService.save(user);

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN','PHARMACY_ADMIN', 'SUPPLIER', 'DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/loggedFirstTime/{username}")
	public ResponseEntity<Object> loggedFirstTime(@PathVariable("username") String username) {

		User user = userService.findByUsername(username);
		
		if (user == null) 
		{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(user.isLoggedFirstTime(), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN','PHARMACY_ADMIN', 'SUPPLIER', 'DERMATOLOGIST', 'PHARMACIST')")
	@PostMapping(value = "/firstTimeChangePassword", consumes = "application/json")
	public ResponseEntity<Boolean> loggedFirstTime(@RequestBody LoginDTO loginDTO)
	{
		User user = userService.findByUsername(loginDTO.getUsername());
		
		user.setPassword(passwordEncoder.encode(loginDTO.getPassword()));
		
		user.setLoggedFirstTime(true);
		
		userService.save(user);
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN','PHARMACY_ADMIN', 'SUPPLIER')")
	@GetMapping(value = "/{username}")
	public ResponseEntity<UserDTO> getOneByUsername(@PathVariable("username") String username) 
	{
		User user = userService.findByUsername(username);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('SYSTEM_ADMIN','PHARMACY_ADMIN', 'SUPPLIER')")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) 
	{

		User user = userService.findByUsername(userDTO.getUsername());

		if (user == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setUsername(userDTO.getUsername());
		user.setLocation(userDTO.getLocation());
		
		userService.save(user);
		locationService.save(user.getLocation());
		
		return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
	}
	
}
