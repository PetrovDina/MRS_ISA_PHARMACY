package mrsisa12.pharmacy.model;

import javax.persistence.Entity;

import mrsisa12.pharmacy.model.enums.UserRole;
import mrsisa12.pharmacy.model.enums.UserStatus;


@Entity
public class PharmacyAdmin extends User {

	public PharmacyAdmin() {
	}

	public PharmacyAdmin(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, UserStatus activeStatus, UserRole userRole, boolean deleted) {
		super(id, username, password, email, firstName, lastName, location, activeStatus, userRole, deleted);
	}

}
