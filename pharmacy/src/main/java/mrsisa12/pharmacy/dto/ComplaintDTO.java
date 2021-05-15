package mrsisa12.pharmacy.dto;

public class ComplaintDTO {
	
	private Long id;
	
	private String patientUsername;
	
	private Long systemAdminId;

	private String content;

	private String response;
	
	public ComplaintDTO() { }
	
	public ComplaintDTO(Long id, String patientUsername, Long systemAdminId, String content, String response) {
		super();
		this.id = id;
		this.patientUsername = patientUsername;
		this.systemAdminId = systemAdminId;
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

	public Long getSystemAdminId() {
		return systemAdminId;
	}

	public void setSystemAdminId(Long systemAdminId) {
		this.systemAdminId = systemAdminId;
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
