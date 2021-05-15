package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class SystemAdmin extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "systemAdmin", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Complaint> complaints;

	public SystemAdmin() {
	}

	public SystemAdmin(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
	}
}
