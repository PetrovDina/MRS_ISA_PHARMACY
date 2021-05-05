package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.User;
import mrsisa12.pharmacy.model.enums.Gender;

public class PharmacyAdminDTO extends UserDTO {

	Long pharmacyId;
	
	public PharmacyAdminDTO() {
		// TODO Auto-generated constructor stub
	}

	public PharmacyAdminDTO(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, Long pharmacyId) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.pharmacyId = pharmacyId;
	}

	public PharmacyAdminDTO(User user) {
		super(user);
		// TODO Auto-generated constructor stub
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}
	
	
}
