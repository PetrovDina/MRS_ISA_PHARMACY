package mrsisa12.pharmacy.complaint.dto;

public abstract class ComplaintAdminDTO {

	private Long id;
	private String content;
	private String patientUsername;
	private String patientEmail;
	private String patientFirstName;
	private String patientLastName;
	
	public ComplaintAdminDTO() { }
	
	public ComplaintAdminDTO(Long id, String content, String patientUsername, String patientEmail,
			String patientFirstName, String patientLastName) {
		super();
		this.id = id;
		this.content = content;
		this.patientUsername = patientUsername;
		this.patientEmail = patientEmail;
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPatientUsername() {
		return patientUsername;
	}

	public void setPatientUsername(String patientUsername) {
		this.patientUsername = patientUsername;
	}

	public String getPatientEmail() {
		return patientEmail;
	}

	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}

	public String getPatientFirstName() {
		return patientFirstName;
	}

	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}

	public String getPatientLastName() {
		return patientLastName;
	}

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}
	
}
