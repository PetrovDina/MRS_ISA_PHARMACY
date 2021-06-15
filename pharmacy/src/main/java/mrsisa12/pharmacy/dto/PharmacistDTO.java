package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Pharmacist;

public class PharmacistDTO extends EmployeeDTO {

	public PharmacistDTO() {

	}

	public PharmacistDTO(Pharmacist pharmacist) {
		super(pharmacist.getId(), pharmacist.getUsername(), pharmacist.getPassword(), pharmacist.getEmail(),
				pharmacist.getFirstName(), pharmacist.getLastName(), pharmacist.getLocation(),
				pharmacist.getGender(), pharmacist.getRating());
	}
}
