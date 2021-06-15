package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.enums.MedicationForm;

public class MedicationDetailsDTO extends MedicationDTO {

	Integer loyaltyPoints;
	
	public MedicationDetailsDTO() { }

	public MedicationDetailsDTO(Medication medication) {
		super(medication);
		this.loyaltyPoints = medication.getLoyaltyPoints();
	}

	public MedicationDetailsDTO(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form,
			double rating, Integer loyaltyPoints) {
		super(id, name, manufacturer, prescriptionReq, form, rating);
		this.loyaltyPoints = loyaltyPoints;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

}
