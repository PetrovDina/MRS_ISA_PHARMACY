package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class PharmacyAdmin extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Pharmacy pharmacy;

	public PharmacyAdmin() {}

	public PharmacyAdmin(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
