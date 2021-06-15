package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.PatientCategory;

public class PatientDTO extends UserDTO {

	private Integer penaltyPoints;
	private Integer loyaltyPoints;
	private PatientCategory category;

	public PatientDTO() {
	}

	public PatientDTO(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			Gender gender, Integer penaltyPoints, Integer loyalty, PatientCategory categ) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.penaltyPoints = penaltyPoints;
		this.loyaltyPoints = loyalty;
		this.category = categ;
	}

	public PatientDTO(Patient patient) {
		this(patient.getId(), patient.getUsername(), patient.getPassword(),  patient.getEmail(), patient.getFirstName(), patient.getLastName(),
				patient.getLocation(), patient.getGender(), patient.getPenaltyPoints(), patient.getLoyaltyPoints(), patient.getCategory());
	}
	
	

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public PatientCategory getCategory() {
		return category;
	}

	public void setCategory(PatientCategory category) {
		this.category = category;
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}

}
