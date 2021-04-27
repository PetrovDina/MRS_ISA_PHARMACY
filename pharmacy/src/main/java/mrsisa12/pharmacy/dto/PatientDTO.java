package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.Gender;

public class PatientDTO extends UserDTO {

	private Integer penaltyPoints;

	public PatientDTO() {
	}

	public PatientDTO(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			Gender gender, Integer penaltyPoints) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.penaltyPoints = penaltyPoints;
	}

	public PatientDTO(Patient patient) {
		this(patient.getId(), patient.getUsername(), patient.getPassword(),  patient.getEmail(), patient.getFirstName(), patient.getLastName(),
				patient.getLocation(), patient.getGender(), patient.getPenaltyPoints());
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}

}
