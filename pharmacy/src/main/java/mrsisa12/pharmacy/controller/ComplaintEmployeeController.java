package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.complaint.dto.ComplaintAdminEmployeeDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintAdminEmployeeResponseDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintEmployeeDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintUserEmployeeDTO;
import mrsisa12.pharmacy.complaint.dto.ComplaintUserEmployeeResponseDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.service.ComplaintEmployeeService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.SystemAdminService;

@RestController
@RequestMapping("/complaintEmployee")
public class ComplaintEmployeeController {
	
	@Autowired
	ComplaintEmployeeService complaintEmployeeService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	SystemAdminService systemAdminService;
	
	@Autowired
	EmailService emailService;
	
	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<ComplaintEmployeeDTO> savePharmacyComplaint(@RequestBody ComplaintEmployeeDTO complaintDTO)
	{	
		ComplaintEmployee complaintEmployee = new ComplaintEmployee();
		
		complaintEmployee.setPatient(patientService.findByUsername(complaintDTO.getPatientUsername()));
		complaintEmployee.setEmployee(employeeService.findOneByUsername(complaintDTO.getEmployeeUsername()));
		complaintEmployee.setContent(complaintDTO.getContent());
		
		complaintEmployeeService.save(complaintEmployee);
		
		ComplaintEmployeeDTO ret = new ComplaintEmployeeDTO();
		ret.setId(complaintEmployee.getId());
		ret.setPatientUsername(complaintEmployee.getPatient().getUsername());
		ret.setEmployeeUsername(complaintEmployee.getEmployee().getUsername());
		ret.setContent(complaintEmployee.getContent());
		
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@GetMapping(value = "/all/forResponse")
	public ResponseEntity<List<ComplaintAdminEmployeeDTO>> getEmployeeComplaintForResponse()
	{
		
		List<ComplaintEmployee> complaints = complaintEmployeeService.findAllByAdminNull();
		List<ComplaintAdminEmployeeDTO> complaintDTOs = new ArrayList<ComplaintAdminEmployeeDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintEmployee complaint : complaints) 
		{
			complaintDTOs.add(new ComplaintAdminEmployeeDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PutMapping(value = "/update", consumes = "application/json")
	public ResponseEntity<ComplaintEmployeeDTO> updateComplaint(@RequestBody ComplaintEmployeeDTO complaintDTO) 
	{
		ComplaintEmployee complaintEmployee = complaintEmployeeService.findOneById(complaintDTO.getId());
		SystemAdmin systemAdmin = systemAdminService.findOneByUsername(complaintDTO.getSystemAdminUsername());
		
		complaintEmployee.setResponse(complaintDTO.getResponse());
		complaintEmployee.setSystemAdmin(systemAdmin);
		
		complaintEmployeeService.save(complaintEmployee);
		
		Patient patient = patientService.findByUsername(complaintDTO.getPatientUsername());
		Employee employee = employeeService.findOneByUsername(complaintDTO.getEmployeeUsername());
		emailService.sendEmailToPatientComplaintEmployeeResponse(patient, systemAdmin, employee);

		return new ResponseEntity<>(new ComplaintEmployeeDTO(complaintEmployee), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@GetMapping(value = "/all/{username}")
	public ResponseEntity<List<ComplaintAdminEmployeeResponseDTO>> getEmployeeComplaintResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintAdminEmployeeResponseDTO na osnovu admina
		 * */
		
		SystemAdmin systemAdmin = systemAdminService.findOneByUsername(username);
		List<ComplaintEmployee> complaints = complaintEmployeeService.findAllBySystemAdmin(systemAdmin);
		List<ComplaintAdminEmployeeResponseDTO> complaintDTOs = new ArrayList<ComplaintAdminEmployeeResponseDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintEmployee complaint : complaints) 
		{
			complaintDTOs.add(new ComplaintAdminEmployeeResponseDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/NotResponsed/{username}")
	public ResponseEntity<List<ComplaintUserEmployeeDTO>> getEmployeeComplaintUserNotResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintUserEmlpoyeeDTO na koje admin nije odgovorio za pacijenta
		 * */
		
		Patient patient = patientService.findByUsername(username);
		List<ComplaintEmployee> complaints = complaintEmployeeService.findAllByPatient(patient);
		List<ComplaintUserEmployeeDTO> complaintDTOs = new ArrayList<ComplaintUserEmployeeDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintEmployee complaint : complaints) 
		{
			if(complaint.getSystemAdmin() == null)
				complaintDTOs.add(new ComplaintUserEmployeeDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/Responsed/{username}")
	public ResponseEntity<List<ComplaintUserEmployeeResponseDTO>> getEmployeeComplaintUserResponsed(@PathVariable String username)
	{
		/*
		 * Vraca listu ComplaintEmployeePharmacyDTO na koje je admin odgovorio za pacijenta
		 * */
		
		Patient patient = patientService.findByUsername(username);
		List<ComplaintEmployee> complaints = complaintEmployeeService.findAllByPatient(patient);
		List<ComplaintUserEmployeeResponseDTO> complaintDTOs = new ArrayList<ComplaintUserEmployeeResponseDTO>();
		
		if(complaints == null) return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
		
		for (ComplaintEmployee complaint : complaints) 
		{
			if(complaint.getSystemAdmin() != null)
				complaintDTOs.add(new ComplaintUserEmployeeResponseDTO(complaint));
		}
		
		return new ResponseEntity<>(complaintDTOs, HttpStatus.OK);
	}

}
