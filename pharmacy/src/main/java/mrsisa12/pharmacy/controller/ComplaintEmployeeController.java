package mrsisa12.pharmacy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ComplaintEmployeeDTO;
import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.service.ComplaintEmployeeService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.PatientService;

@RestController
@RequestMapping("/complaintEmployee")
public class ComplaintEmployeeController {
	
	@Autowired
	ComplaintEmployeeService complaintEmployeeService;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PatientService patientService;
	
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<ComplaintEmployeeDTO> savePharmacyComplaint(@RequestBody ComplaintEmployeeDTO complaintDTO)
	{	
		ComplaintEmployee complaintEmployee = new ComplaintEmployee();
		
		complaintEmployee.setPatient(patientService.findByUsername(complaintDTO.getPatientUsername()));
		complaintEmployee.setEmployee(employeeService.findOne(complaintDTO.getEmployeeId()));
		complaintEmployee.setContent(complaintDTO.getContent());
		
		complaintEmployeeService.save(complaintEmployee);
		
		ComplaintEmployeeDTO ret = new ComplaintEmployeeDTO();
		ret.setId(complaintEmployee.getId());
		ret.setPatientUsername(complaintEmployee.getPatient().getUsername());
		ret.setEmployeeId(complaintEmployee.getEmployee().getId());
		ret.setContent(complaintEmployee.getContent());
		
		return new ResponseEntity<>(ret, HttpStatus.CREATED);
	}

}
