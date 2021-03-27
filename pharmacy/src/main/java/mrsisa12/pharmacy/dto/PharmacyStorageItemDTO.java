package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyStorageItemDTO {
	
	private Long id;
	private int quantity;
	private Long medicationId;
	private Long pharmacyId;
	
	public PharmacyStorageItemDTO() {
		
	}
	
	public PharmacyStorageItemDTO(PharmacyStorageItem pharmacyStorageItem){
        this.id = pharmacyStorageItem.getId();
        this.medicationId = pharmacyStorageItem.getMedication().getId();
        this.pharmacyId = pharmacyStorageItem.getPharmacy().getId();
        this.quantity = pharmacyStorageItem.getQuantity();
    }
	
	

	public PharmacyStorageItemDTO(Long id, int quantity, Long medication, Long pharmacy) {
		this.id = id;
		this.quantity = quantity;
		this.medicationId = medication;
		this.pharmacyId = pharmacy;
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

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

}
