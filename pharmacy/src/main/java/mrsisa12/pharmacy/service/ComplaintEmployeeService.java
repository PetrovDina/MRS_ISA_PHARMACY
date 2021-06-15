package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.repository.ComplaintEmployeeRepository;

@Service
public class ComplaintEmployeeService {
	
	@Autowired
	private ComplaintEmployeeRepository complaintEmployeeRepository;
	
	@Autowired
	EmployeeService employeeService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	SystemAdminService systemAdminService;
	
	@Autowired
	EmailService emailService;
	
	public List<ComplaintEmployee> findAll() 
	{
		return complaintEmployeeRepository.findAll();
	}
	
	public void save(ComplaintEmployee complaint)
	{
		complaintEmployeeRepository.save(complaint);
	}
	
	public List<ComplaintEmployee> findAllByAdminNull()
	{
		return complaintEmployeeRepository.findAllByAdminNull();
	}
	
	public ComplaintEmployee findOneById(Long id) 
	{
		return complaintEmployeeRepository.findOneById(id);
	}
	
	public List<ComplaintEmployee> findAllBySystemAdmin(SystemAdmin systemAdmin)
	{
		return complaintEmployeeRepository.findAllBySystemAdmin(systemAdmin);
	}

	public List<ComplaintEmployee> findAllByPatient(Patient patient) 
	{
		return complaintEmployeeRepository.findAllByPatient(patient);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean responseToComplaint(ComplaintEmployee complaintEmployee, SystemAdmin systemAdmin, String response,
			String patientUsername, String employeeUsername)
	{
		
		if(complaintEmployee.getResponse() != null)
		{
			return false;
		}
		
		complaintEmployee.setResponse(response);
		complaintEmployee.setSystemAdmin(systemAdmin);
		
		save(complaintEmployee);
		
		Patient patient = patientService.findByUsername(patientUsername);
		Employee employee = employeeService.findOneByUsername(employeeUsername);
		emailService.sendEmailToPatientComplaintEmployeeResponse(patient, systemAdmin, employee);
		
		return true;
	}

}
