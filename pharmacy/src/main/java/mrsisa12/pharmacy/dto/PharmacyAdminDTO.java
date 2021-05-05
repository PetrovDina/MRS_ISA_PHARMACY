package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.enums.Gender;

public class PharmacyAdminDTO extends UserDTO {

	private Long pharmacyId;

	public PharmacyAdminDTO() {}

	public PharmacyAdminDTO(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, Long pharmacyId) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.pharmacyId = pharmacyId;
	}

	public PharmacyAdminDTO(PharmacyAdmin pharmacyAdmin) {
		this(pharmacyAdmin.getId(), pharmacyAdmin.getUsername(), pharmacyAdmin.getPassword(), pharmacyAdmin.getEmail(),
				pharmacyAdmin.getFirstName(), pharmacyAdmin.getLastName(), pharmacyAdmin.getLocation(),
				pharmacyAdmin.getGender(), pharmacyAdmin.getPharmacy().getId());
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
