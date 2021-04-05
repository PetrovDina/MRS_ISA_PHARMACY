package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserRole;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Patient extends User {

	@Column(name = "penaltyPoints", unique=false, nullable=false)
	Integer penaltyPoints;
	
	public Patient() { }

	public Patient(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, UserRole userRole, boolean deleted, Integer penaltyPoints) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRole, deleted);
		this.penaltyPoints = penaltyPoints;
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}


}
