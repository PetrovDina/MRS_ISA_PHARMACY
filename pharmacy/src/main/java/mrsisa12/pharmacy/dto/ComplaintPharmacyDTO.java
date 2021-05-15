package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintPharmacyDTO extends ComplaintDTO {
	
	Long pharmacyId;
	
	public ComplaintPharmacyDTO() {}

	public ComplaintPharmacyDTO(Long id, String patientId, Long systemAdminId, String content, String response, Long pharmacyId) {
		super(id, patientId, systemAdminId, content, response);
		this.pharmacyId = pharmacyId;
	}
	
	public ComplaintPharmacyDTO(ComplaintPharmacy complaintPharmacy) {
		this(complaintPharmacy.getId(), complaintPharmacy.getPatient().getUsername(), complaintPharmacy.getSystemAdmin().getId(),
				complaintPharmacy.getContent(), complaintPharmacy.getResponse(), complaintPharmacy.getPharmacy().getId());
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
