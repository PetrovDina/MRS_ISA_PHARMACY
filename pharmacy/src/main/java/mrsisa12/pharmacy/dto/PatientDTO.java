package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Patient;

public class PatientDTO {
	
	private Long id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private Integer penaltyPoints;
	
	public PatientDTO() { }
	
	public PatientDTO(Long id, String username, String password, String email, String firstName, String lastName, Integer penaltyPoints) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.penaltyPoints = penaltyPoints;
	}
	
	public PatientDTO(Patient patient)
	{
		this.id = patient.getId();
		this.username = patient.getUsername();
		this.password = patient.getPassword();
		this.email = patient.getEmail();
		this.firstName = patient.getFirstName();
		this.lastName = patient.getLastName();
		this.penaltyPoints = patient.getPenaltyPoints();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}
	
}
