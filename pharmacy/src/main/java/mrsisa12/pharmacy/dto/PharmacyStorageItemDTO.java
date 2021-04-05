package mrsisa12.pharmacy.dto;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyStorageItemDTO {
	
	private Long id;
	private int quantity;
	private List<ItemPriceDTO> itemPrices;
	private MedicationDTO medication;
	
	public PharmacyStorageItemDTO() {
		this.itemPrices = new ArrayList<ItemPriceDTO>();
	}

	public PharmacyStorageItemDTO(Long id, int quantity, Medication medication) {
		this();
		this.id = id;
		this.quantity = quantity;
		this.medication = new MedicationDTO(medication);
	}
	
	public PharmacyStorageItemDTO(PharmacyStorageItem pharmacyStorageItem){
        this(pharmacyStorageItem.getId(), pharmacyStorageItem.getQuantity(), pharmacyStorageItem.getMedication());
        fillItemPrices(pharmacyStorageItem.getItemPrices());
    }

	private void fillItemPrices(List<ItemPrice> itemPricesList) {
		for(ItemPrice ip : itemPricesList) {
			this.itemPrices.add(new ItemPriceDTO(ip));
		}
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

	public List<ItemPriceDTO> getItemPrices() {
		return itemPrices;
	}

	public void setItemPrices(List<ItemPriceDTO> itemPrices) {
		this.itemPrices = itemPrices;
	}

	public MedicationDTO getMedication() {
		return medication;
	}

	public void setMedication(MedicationDTO medication) {
		this.medication = medication;
	}
	
}
