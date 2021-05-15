package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PlainEmployeeDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EmployeeService;
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

}
