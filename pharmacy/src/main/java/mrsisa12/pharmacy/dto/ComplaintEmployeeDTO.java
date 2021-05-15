package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ComplaintEmployee;

public class ComplaintEmployeeDTO extends ComplaintDTO {

	Long employeeId;
	
	public ComplaintEmployeeDTO() { }

	public ComplaintEmployeeDTO(Long id, String patientUsername, Long systemAdminId, String content, String response, Long employeeId) {
		super(id, patientUsername, systemAdminId, content, response);
		this.employeeId = employeeId;
	}
	
	public ComplaintEmployeeDTO(ComplaintEmployee c) {
		this(c.getId(), c.getPatient().getUsername(), c.getSystemAdmin().getId(), c.getContent(), c.getResponse(), c.getEmployee().getId());
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
