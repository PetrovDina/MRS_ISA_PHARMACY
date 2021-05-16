package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintAdminPharmacyDTO extends ComplaintAdminDTO {

	private Long pharmacyId;
	private String pharmacyName;

	public ComplaintAdminPharmacyDTO() 
	{
		super();
	}

	public ComplaintAdminPharmacyDTO(Long id, String content, String patientUsername, String patientEmail,
			String patientFirstName, String patientLastName, Long pharmacyId, String pharmacyName) {
		super(id, content, patientUsername, patientEmail, patientFirstName, patientLastName);
		this.pharmacyId = pharmacyId;
		this.pharmacyName = pharmacyName;
	}
	
	public ComplaintAdminPharmacyDTO(ComplaintPharmacy c) 
	{
		this(c.getId(), c.getContent(), c.getPatient().getUsername(), c.getPatient().getEmail(), c.getPatient().getFirstName(),
				c.getPatient().getLastName(), c.getPharmacy().getId(), c.getPharmacy().getName());
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public String getPharmacyName() {
		return pharmacyName;
	}

	public void setPharmacyName(String pharmacyName) {
		this.pharmacyName = pharmacyName;
	}
	
}
