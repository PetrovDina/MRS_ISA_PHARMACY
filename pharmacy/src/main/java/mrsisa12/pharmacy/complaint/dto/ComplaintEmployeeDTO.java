package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.model.ComplaintEmployee;

public class ComplaintEmployeeDTO extends ComplaintDTO {

	String employeeUsername;
	
	public ComplaintEmployeeDTO() { }

	public ComplaintEmployeeDTO(Long id, String patientUsername, String systemAdminUsername, String content, String response, String employeeUsername) {
		super(id, patientUsername, systemAdminUsername, content, response);
		this.employeeUsername = employeeUsername;
	}
	
	public ComplaintEmployeeDTO(ComplaintEmployee c) {
		this(c.getId(), c.getPatient().getUsername(), c.getSystemAdmin().getUsername(), c.getContent(), c.getResponse(), c.getEmployee().getUsername());
	}

	public String getEmployeeUsername() {
		return employeeUsername;
	}

	public void setEmployeeUsername(String employeeUsername) {
		this.employeeUsername = employeeUsername;
	}

}
