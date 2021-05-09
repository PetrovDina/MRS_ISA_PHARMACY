package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.SupplierStorageItem;

public class SupplierStorageItemDTO {
	
	private Long id;
	private int quantity;
	private MedicationDTO medication;
	private Long supplierId;
	
	public SupplierStorageItemDTO() { }

	public SupplierStorageItemDTO(Long id, int quantity, Medication medication, Long supplierId) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.medication = new MedicationDTO(medication);
		this.supplierId = supplierId;
	}
	
	public SupplierStorageItemDTO(SupplierStorageItem supplierStorageItem) {
		this(supplierStorageItem.getId(), supplierStorageItem.getQuantity(), supplierStorageItem.getMedication(),
				supplierStorageItem.getSupplier().getId());
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

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

}
