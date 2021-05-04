package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Dermatologist;

public class DermatologistDTO extends EmployeeDTO {

	public DermatologistDTO() {

	}

	public DermatologistDTO(Dermatologist dermatologist) {
		super(dermatologist.getId(), dermatologist.getUsername(), dermatologist.getPassword(), dermatologist.getEmail(),
				dermatologist.getFirstName(), dermatologist.getLastName(), dermatologist.getLocation(),
				dermatologist.getGender(), dermatologist.getRating());
	}
}
