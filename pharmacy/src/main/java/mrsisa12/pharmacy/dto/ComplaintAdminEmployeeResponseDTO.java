package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ComplaintEmployee;

public class ComplaintAdminEmployeeResponseDTO extends ComplaintAdminEmployeeDTO {
	
	String response;

	public ComplaintAdminEmployeeResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public ComplaintAdminEmployeeResponseDTO(Long id, String content, String patientUsername, String patientEmail,
			String patientFirstName, String patientLastName, String employeeUsername, String employeeWorkAs, String response) {
		super(id, content, patientUsername, patientEmail, patientFirstName, patientLastName, employeeUsername,
				employeeWorkAs);
		this.response = response;
	}

	public ComplaintAdminEmployeeResponseDTO(ComplaintEmployee c) {
		super(c);
		this.response = c.getResponse();
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
