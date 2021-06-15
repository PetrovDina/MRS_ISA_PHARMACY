package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintAdminPharmacyResponseDTO extends ComplaintAdminPharmacyDTO {
	
	private String response;

	public ComplaintAdminPharmacyResponseDTO() { }

	public ComplaintAdminPharmacyResponseDTO(Long id, String content, String patientUsername, String patientEmail,
			String patientFirstName, String patientLastName, Long pharmacyId, String pharmacyName, String response) {
		super(id, content, patientUsername, patientEmail, patientFirstName, patientLastName, pharmacyId, pharmacyName);
		this.response = response;
	}

	public ComplaintAdminPharmacyResponseDTO(ComplaintPharmacy c) {
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
