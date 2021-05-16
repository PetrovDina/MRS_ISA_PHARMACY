package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PlainPharmacyDTO;
import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintUserPharmacyResponseDTO extends ComplaintUserPharmacyDTO {
	
	private String systemAdminUsername;
	private String response;

	public ComplaintUserPharmacyResponseDTO() { }

	public ComplaintUserPharmacyResponseDTO(Long id, String content, PlainPharmacyDTO pharmacy, String systemAdminUsername
			, String response) {
		super(id, content, pharmacy);
		this.systemAdminUsername = systemAdminUsername;
		this.response = response;
	}

	public ComplaintUserPharmacyResponseDTO(ComplaintPharmacy c) {
		this(c.getId(), c.getContent(), new PlainPharmacyDTO(c.getPharmacy()), c.getSystemAdmin().getUsername(), c.getResponse());
	}

	public String getSystemAdminUsername() {
		return systemAdminUsername;
	}

	public void setSystemAdminUsername(String systemAdminUsername) {
		this.systemAdminUsername = systemAdminUsername;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
