package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Patient extends User {


	private static final long serialVersionUID = 1L;
	
	@Column(name = "penaltyPoints", unique=false, nullable=false)
	Integer penaltyPoints;
	
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "allergies",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"))
    private List<Medication> allergies;
	
	
	public List<Medication> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<Medication> allergies) {
		this.allergies = allergies;
	}

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
