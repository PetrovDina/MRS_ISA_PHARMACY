package mrsisa12.pharmacy.dto;

import java.util.List;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.enums.MedicationForm;

public class MedicationCreationDTO extends MedicationDTO {

	private List<MedicationDTO> alternatives;
	private String description;
	private String content;
	private Integer loyaltyPoints;
	
	public MedicationCreationDTO() { super(); }

	public MedicationCreationDTO(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form, List<MedicationDTO> alternatives, String description, String content, double rating, Integer loyaltyPoints) {
		super(id, name, manufacturer, prescriptionReq, form, rating);
		this.alternatives = alternatives;
		this.description = description;
		this.content = content;
		this.loyaltyPoints = loyaltyPoints;
	}
	
	public MedicationCreationDTO(Medication m)
	{	
		this(m.getId(), m.getName(), m.getManufacturer(), m.isPrescriptionReq(), m.getForm(), m.getDTOAlternatives(), m.getDescription(), m.getContent(), m.getRating(), m.getLoyaltyPoints());
	}

	public List<MedicationDTO> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<MedicationDTO> alternatives) {
		this.alternatives = alternatives;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	
}
