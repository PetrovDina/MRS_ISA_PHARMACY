package mrsisa12.pharmacy.complaint.dto;

public class ComplaintDTO {
	
	private Long id;
	
	private String patientUsername;
	
	private String systemAdminUsername;

	private String content;

	private String response;
	
	public ComplaintDTO() { }
	
	public ComplaintDTO(Long id, String patientUsername, String systemAdminUsername, String content, String response) {
		super();
		this.id = id;
		this.patientUsername = patientUsername;
		this.systemAdminUsername = systemAdminUsername;
		this.content = content;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPatientUsername() {
		return patientUsername;
	}

	public void setPatientUsername(String patientUsername) {
		this.patientUsername = patientUsername;
	}

	public String getSystemAdminUsername() {
		return systemAdminUsername;
	}

	public void setSystemAdminUsername(String systemAdminUsername) {
		this.systemAdminUsername = systemAdminUsername;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
	
	
}
