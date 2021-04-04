package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.PrescriptionItem;

public class PrescriptionItemDTO {

	private Long id;
	private int quantity;
	private Long medicationId;
	
public PrescriptionItemDTO() {
		
	}
	
	public PrescriptionItemDTO(PrescriptionItem reservationItem){
        this.id = reservationItem.getId();
        this.medicationId = reservationItem.getMedication().getId();
        this.quantity = reservationItem.getQuantity();
    }
	
	

	public PrescriptionItemDTO(Long id, int quantity, Long medication) {
		this.id = id;
		this.quantity = quantity;
		this.medicationId = medication;
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

	public Long getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(Long medicationId) {
		this.medicationId = medicationId;
	}


}
