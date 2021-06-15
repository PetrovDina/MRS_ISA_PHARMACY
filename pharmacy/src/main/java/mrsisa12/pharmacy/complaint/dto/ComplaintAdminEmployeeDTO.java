package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.model.Dermatologist;

public class ComplaintAdminEmployeeDTO extends ComplaintAdminDTO {
	
	String employeeUsername;
	String employeeWorkAs;

	public ComplaintAdminEmployeeDTO() { }

	public ComplaintAdminEmployeeDTO(Long id, String content, String patientUsername, String patientEmail,
			String patientFirstName, String patientLastName, String employeeUsername, String employeeWorkAs) {
		super(id, content, patientUsername, patientEmail, patientFirstName, patientLastName);
		this.employeeUsername = employeeUsername;
		this.employeeWorkAs = employeeWorkAs;
	}
	
	public ComplaintAdminEmployeeDTO(ComplaintEmployee c) 
	{
		this(c.getId(), c.getContent(), c.getPatient().getUsername(), c.getPatient().getEmail(), c.getPatient().getFirstName(),
				c.getPatient().getLastName(), c.getEmployee().getUsername(),
				(c.getEmployee() instanceof Dermatologist) ? "Dermatologist" : "Pharmacist");
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}

	public String getEmployeeWorkAs() {
		return employeeWorkAs;
	}

	public void setEmployeeWorkAs(String employeeWorkAs) {
		this.employeeWorkAs = employeeWorkAs;
	}

}
