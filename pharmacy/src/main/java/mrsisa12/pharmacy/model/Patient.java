package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Patient extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "penaltyPoints", unique=false, nullable=false)
	Integer penaltyPoints;
	
	public Patient() { }

	public Patient(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, Integer penaltyPoints) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
		this.penaltyPoints = penaltyPoints;
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}

}
