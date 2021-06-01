package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.EPrescriptionItem;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.TherapyItem;

public class TherapyItemDTO {

	private Long id;
	private int quantity;
	private MedicationDTO medication;
	private int therapyDuration;
	
	public TherapyItemDTO() {
		
	}	

	public TherapyItemDTO(Long id, int quantity, Medication medication, int therapyDuration) {
		this.id = id;
		this.quantity = quantity;
		this.medication = new MedicationDTO(medication);
		this.therapyDuration = therapyDuration;
	}
	
	public TherapyItemDTO(TherapyItem reservationItem){
		this(reservationItem.getId(), reservationItem.getQuantity(), reservationItem.getMedication(),
				reservationItem.getTherapyDuration());
    }
	
	public TherapyItemDTO(EPrescriptionItem e) {
		this(e.getId(), e.getQuantity(), e.getMedication(),
				0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public MedicationDTO getMedication() {
		return medication;
	}

	public void setMedication(MedicationDTO medication) {
		this.medication = medication;
	}

	public int getTherapyDuration() {
		return therapyDuration;
	}

	public void setTherapyDuration(int therapyDuration) {
		this.therapyDuration = therapyDuration;
	}

}
