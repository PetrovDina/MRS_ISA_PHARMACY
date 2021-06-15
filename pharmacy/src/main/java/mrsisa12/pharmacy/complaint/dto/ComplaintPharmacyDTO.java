package mrsisa12.pharmacy.complaint.dto;

import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintPharmacyDTO extends ComplaintDTO {
	
	Long pharmacyId;
	
	public ComplaintPharmacyDTO() {}

	public ComplaintPharmacyDTO(Long id, String patientId, String systemAdminUsername, String content, String response, Long pharmacyId) {
		super(id, patientId, systemAdminUsername, content, response);
		this.pharmacyId = pharmacyId;
	}
	
	public ComplaintPharmacyDTO(ComplaintPharmacy complaintPharmacy) {
		this(complaintPharmacy.getId(), complaintPharmacy.getPatient().getUsername(), complaintPharmacy.getSystemAdmin().getUsername(),
				complaintPharmacy.getContent(), complaintPharmacy.getResponse(), complaintPharmacy.getPharmacy().getId());
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
