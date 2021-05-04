package mrsisa12.pharmacy.dto.pharmacy;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.EmploymentDTO;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Pharmacy;

public class PharmacyWithEmploymentsDTO extends PharmacyDTO {

	private List<EmploymentDTO> employments;

	public PharmacyWithEmploymentsDTO() {}
	
	public PharmacyWithEmploymentsDTO(Pharmacy pharmacy) {
		super(pharmacy);
		fillPharmacyWithEmployments(pharmacy.getEmployments());
	}
	
	private void fillPharmacyWithEmployments(List<Employment> employments) {
		if (this.employments == null) {
			this.employments = new ArrayList<EmploymentDTO>();
		}
		for (Employment employment : employments) {
			this.employments.add(new EmploymentDTO(employment));
		}
	}
	
	
	public List<EmploymentDTO> getEmployments() {
		return employments;
	}

	public void setEmployments(List<EmploymentDTO> employments) {
		this.employments = employments;
	}
	
	
}
