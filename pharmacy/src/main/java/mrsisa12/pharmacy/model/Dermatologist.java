package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Dermatologist extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// unique = true; izmenio sam na false
	@Column(name = "dermatologistNickname", unique=false, nullable=true)
	private String dermatologistNickname;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
	private List<Employment> employments;
	
	public Dermatologist() {}

	public Dermatologist(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, double rating, String dermatologistNickname) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted, rating);
		this.dermatologistNickname = dermatologistNickname;
	}

	public String getDermatologistNickname() {
		return dermatologistNickname;
	}

	public void setDermatologistNickname(String dermatologistNickname) {
		this.dermatologistNickname = dermatologistNickname;
	}
	
	public List<Employment> getEmployments() {
		return employments;
	}

	public void setEmployments(List<Employment> employments) {
		this.employments = employments;
	}
}
