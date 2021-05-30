package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.EmploymentDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.EmploymentContractType;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.EmploymentService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacistService;
import mrsisa12.pharmacy.service.PharmacyService;

@RestController
@RequestMapping("/employments")
public class EmploymentController {

	@Autowired
	private EmploymentService employmentService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PharmacistService pharmacistService;

	@Autowired
	private PharmacyService pharmacyService;
	

	@GetMapping(value = "/all")
	public ResponseEntity<List<EmploymentDTO>> getAllEmployments() {

		List<Employment> employments = employmentService.findAll();

		// convert employments to DTOs
		List<EmploymentDTO> employmentsDTO = new ArrayList<>();
		for (Employment employment : employments) {
			employmentsDTO.add(new EmploymentDTO(employment));
		}

		return new ResponseEntity<>(employmentsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/pharmacistEmploymentsByTime")
	public ResponseEntity<List<EmploymentDTO>> getAllPharmacistEmploymentsByTime(
			@RequestParam("startDate") String startDate, @RequestParam String startTime) {

		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalDate endDate2 = LocalDate.parse(startDate);
		LocalTime startTime2 = LocalTime.parse(startTime);
		LocalTime endTime2 = LocalTime.parse(startTime).plusHours(1);
		TimePeriod tp = new TimePeriod(startDate2, startTime2, endDate2, endTime2);
//		List<Employment> employments = employmentService.findAllPharmacistEmploymentsByTime(startTime2);
		List<Employment> employments = employmentService.findAllPharmacistEmployments();

		List<Employment> matches = new ArrayList<Employment>();

		for (Employment e : employments) {
			if (employeeService.checkAppointmentTime(tp, e.getEmployee().getId())) {
				matches.add(e);
			}
		}
		// convert employments to DTOs
		List<EmploymentDTO> employmentsDTO = new ArrayList<>();
		for (Employment employment : matches) {
			System.out.println(employment.getId());
			employmentsDTO.add(new EmploymentDTO(employment));
		}

		return new ResponseEntity<>(employmentsDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<EmploymentDTO>> getEmploymentsPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<Employment> employments = employmentService.findAll(page);

		// convert employments to DTOs
		List<EmploymentDTO> employmentsDTO = new ArrayList<>();
		for (Employment e : employments) {
			employmentsDTO.add(new EmploymentDTO(e));
		}

		return new ResponseEntity<>(employmentsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<EmploymentDTO> getEmployment(@PathVariable Long id) {

		Employment employment = employmentService.findOne(id);

		// employment must exist
		if (employment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new EmploymentDTO(employment), HttpStatus.OK);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<EmploymentDTO> saveEmployment(@RequestBody EmploymentDTO employmentDTO) {
		Employment employment = new Employment();
		// provjera da li se radno vrijeme koje je prosljednjeno poklapa sa radnim
		// vremenom u nekoj drugoj apoteci

		Employee employee = employeeService.findOne(employmentDTO.getEmployee().getId());
		if (employmentDTO.getContractType().compareTo(EmploymentContractType.DERMATOLOGIST_CONTRACT) == 0) {
			boolean res = employmentService.checkOtherWorkTimes(new TimePeriod(employmentDTO.getWorkTime()), employee);
			if (!res)
				return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}

		// postavljanje zaposlenog
		employment.setEmployee(employee);
		// postavljanje apoteke
		Pharmacy pharmacy = pharmacyService.findOne(employmentDTO.getPharmacy().getId());
		employment.setPharmacy(pharmacy);
		// postavljanje radnog vremena
		employment.setWorkTime(new TimePeriod(employmentDTO.getWorkTime()));
		// postavljanje da nije obrisan employment
		employment.setDeleted(false);
		// postavljanje tipa contract-a
		employment.setContractType(EmploymentContractType.valueOf(employmentDTO.getContractType().toString()));

		employmentService.save(employment);

		if (employmentDTO.getContractType().compareTo(EmploymentContractType.PHARMACIST_CONTRACT) == 0) {
			Pharmacist pharmacist = pharmacistService.findOne(employmentDTO.getEmployee().getId());
			pharmacist.setEmployment(employment);
			pharmacistService.save(pharmacist);
		}
		return new ResponseEntity<>(new EmploymentDTO(employment), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<EmploymentDTO> updateEmployment(@RequestBody EmploymentDTO employmentDTO) {

		// an employment must exist
		Employment employment = employmentService.findOne(employmentDTO.getId());

		if (employment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// PRETPOTAVLJAM DA CE SE MOCI UREDJIVATI SAMO VRIJEME

		employment = employmentService.save(employment);
		return new ResponseEntity<>(new EmploymentDTO(employment), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteEmployment(@PathVariable Long id) {

		Employment employment = employmentService.findOne(id);

		if (employment != null) {
			employmentService.deleteEmployment(employment);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/pharmacyOfLoggedInPharmacist")
	public ResponseEntity<PharmacyDTO> getPharmacyOfLoggedInPharmacist(@RequestParam String username) {
		List<Employment> employments = employmentService.findAllPharmacistEmployments();
		Employee emp = employeeService.findOneByUsername(username);
		Pharmacy p = null;
		for (Employment e : employments) {
			if (e.getEmployee().getId() == emp.getId()) {
				p = e.getPharmacy();
			}
		}
		if (p == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PharmacyDTO(p), HttpStatus.OK);
	}
	
	@GetMapping(value = "/pharmaciesOfLoggedInDermatologist")
	public ResponseEntity<List<PharmacyDTO>> getPharmaciesOfLoggedInDermatologist(@RequestParam String username) {
		Employee emp = employeeService.findOneByUsername(username);
		List<Employment> employments = employmentService.findAllEmploymentsOfDermatologist(emp.getId());
		
		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Employment e : employments) {
			pharmaciesDTO.add(new PharmacyDTO(e.getPharmacy()));
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/employmentsOfDermatologist")
	public ResponseEntity<List<EmploymentDTO>> getEmploymentsOfDermatologist(@RequestParam String username) {
		Employee emp = employeeService.findOneByUsername(username);
		List<Employment> employments = employmentService.findAllEmploymentsOfDermatologist(emp.getId());
		
		List<EmploymentDTO> employmentsDTO = new ArrayList<>();
		for (Employment e : employments) {
			employmentsDTO.add(new EmploymentDTO(e));
		}

		return new ResponseEntity<>(employmentsDTO, HttpStatus.OK);
	}
	
}
