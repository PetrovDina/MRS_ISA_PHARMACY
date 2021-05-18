package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.dto.PlainEmployeeDTO;
import mrsisa12.pharmacy.model.ComplaintEmployee;

public class ComplaintUserEmployeeResponseDTO extends ComplaintUserEmployeeDTO {
	
	private String systemAdminUsername;
	private String response;


	public ComplaintUserEmployeeResponseDTO() { }

	public ComplaintUserEmployeeResponseDTO(Long id, String content, PlainEmployeeDTO employee, String systemAdminUsername, String response) {
		super(id, content, employee);
		this.systemAdminUsername = systemAdminUsername;
		this.response = response;
	}

	public ComplaintUserEmployeeResponseDTO(ComplaintEmployee c) 
	{
		super(c);
		this.systemAdminUsername = c.getSystemAdmin().getUsername();
		this.response = c.getResponse();
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getSystemAdminUsername() {
		return systemAdminUsername;
	}

	public void setSystemAdminUsername(String systemAdminUsername) {
		this.systemAdminUsername = systemAdminUsername;
	}

}
