package mrsisa12.pharmacy.dto.pharmacy;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyWithStorageItemsDTO extends PharmacyDTO {

	private List<PharmacyStorageItemDTO> pharmacyStorageItems;

	public PharmacyWithStorageItemsDTO() {
	}

	public PharmacyWithStorageItemsDTO(Pharmacy pharmacy) {
		super(pharmacy);
		fillStorageItems(pharmacy.getPharmacyStorageItems());
	}

	private void fillStorageItems(List<PharmacyStorageItem> pSItems) {
		if (this.pharmacyStorageItems == null)
			this.pharmacyStorageItems = new ArrayList<PharmacyStorageItemDTO>();
		for (PharmacyStorageItem psi : pSItems)
			this.pharmacyStorageItems.add(new PharmacyStorageItemDTO(psi));
	}

	public List<PharmacyStorageItemDTO> getPharmacyStorageItems() {
		return pharmacyStorageItems;
	}

	public void setPharmacyStorageItems(List<PharmacyStorageItemDTO> pharmacyStorageItems) {
		this.pharmacyStorageItems = pharmacyStorageItems;
	}

}
