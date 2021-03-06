package mrsisa12.pharmacy.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.EmployeeDTO;
import mrsisa12.pharmacy.dto.PlainEmployeeDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.EmployeeRating;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EmployeeRatingService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.PatientService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmployeeRatingService employeeRatingService;
	
	@Autowired
	private LocationService locationService;
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/{username}/complaints")
	public ResponseEntity<List<PlainEmployeeDTO>> getEmployeesForComplaint(
			@PathVariable String username) {

		Patient patient = patientService.findByUsername(username);
		List<PlainEmployeeDTO> employeeDTOs = new ArrayList<PlainEmployeeDTO>();
		
		List<Appointment> appointemts = appointmentService.findAllByPatientId(patient.getId());
		for (Appointment appointment : appointemts) 
		{
			if(appointment.getStatus() == AppointmentStatus.CONCLUDED)
			{
				if(!employeeService.containsEmployee(employeeDTOs, appointment.getEmployee()))
				{
					employeeDTOs.add(new PlainEmployeeDTO(appointment.getEmployee()));
				}
					
			}
				
		}

		return new ResponseEntity<>(employeeDTOs, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/rateEmployee")
	public ResponseEntity<Double> rateEmployee(
			@RequestParam String patientUsername, @RequestParam Long employeeId, @RequestParam double ratedValue) {

		if (ratedValue < 0 || ratedValue > 5) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
 
		}
		Patient patient = patientService.findByUsername(patientUsername);
		Employee employee = employeeService.findOne(employeeId);
		
		EmployeeRating rating = employeeRatingService.findOneByPatientAndEmployee(patientUsername, employeeId);
		
		if (rating == null) {
			//create new rating obj
			rating = new EmployeeRating();
			rating.setDate(new Date());
			rating.setEmployee(employee);
			rating.setPatient(patient);
			rating.setRating(ratedValue);
			employeeRatingService.save(rating);
		}
		else {
			//update existing rating obj
			rating.setRating(ratedValue);
			rating.setDate(new Date());
			employeeRatingService.save(rating);		
		}
		
		//updating value in Employee object
		List<EmployeeRating> employeesRatings = employeeRatingService.findAllByEmployee(employeeId);
		int numRatings = employeesRatings.size();
		double newRating = 0;
		
		for (EmployeeRating er : employeesRatings) {
			newRating += er.getRating();
		}
		
		newRating /= numRatings;
		
		DecimalFormat df = new DecimalFormat("#.##");
		newRating = Double.parseDouble(df.format(newRating));
		
		employee.setRating(newRating);
		employeeService.save(employee);

		return new ResponseEntity<Double>(newRating, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/getRating")
	public ResponseEntity<Double> getRating(
			@RequestParam String patientUsername, @RequestParam Long employeeId) {


		EmployeeRating rating = employeeRatingService.findOneByPatientAndEmployee(patientUsername, employeeId);
		
		if (rating == null) {
			return new ResponseEntity<Double>(0.0, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Double>(rating.getRating(), HttpStatus.OK);	
		}

	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/{username}")
	public ResponseEntity<EmployeeDTO> getOneByUsername(@PathVariable("username") String username) {

		Employee employee = employeeService.findOneByUsername(username);
		
		if (employee == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new EmployeeDTO(employee), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@PutMapping(consumes = "application/json")
	public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO) {

		Employee p = employeeService.findOne(employeeDTO.getId());

		if (p == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		p.setFirstName(employeeDTO.getFirstName());
		p.setLastName(employeeDTO.getLastName());
		p.setUsername(employeeDTO.getUsername());
		p.setLocation(employeeDTO.getLocation());
		employeeService.save(p);
		locationService.save(p.getLocation());
		return new ResponseEntity<>(new EmployeeDTO(p), HttpStatus.CREATED);
	}
	
}
