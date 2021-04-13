package mrsisa12.pharmacy.dto.pharmacyStorageItem;

import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyStorageItemDTO {

	private Long id;
	private int quantity;
	private MedicationDTO medication;
	private PharmacyDTO pharmacy;

	public PharmacyStorageItemDTO() {
	}

	public PharmacyStorageItemDTO(Long id, int quantity, Medication medication, Pharmacy pharmacy) {
		this();
		this.id = id;
		this.quantity = quantity;
		this.medication = new MedicationDTO(medication);
		this.pharmacy = new PharmacyDTO(pharmacy);
	}

	public PharmacyStorageItemDTO(PharmacyStorageItem pharmacyStorageItem) {
		this(pharmacyStorageItem.getId(), pharmacyStorageItem.getQuantity(), pharmacyStorageItem.getMedication(),
				pharmacyStorageItem.getPharmacy());
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

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	@Override
	public String toString() {
		return "PharmacyStorageItemDTO [id=" + id + ", quantity=" + quantity + ", medication=" + medication + "]";
	}

}
