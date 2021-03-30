package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.ItemPrice;

public class ItemPriceDTO {

	 private Long id;
	 private double price;
	 private boolean current;
	 //private PharmacyStorageItemDTO pharmacyStorageItemDTO;
	 
	 public ItemPriceDTO() {
		 
	 }
	 
	 public ItemPriceDTO(ItemPrice itemPrice) {
		 id = itemPrice.getId();
		 price = itemPrice.getPrice();
		 current = itemPrice.isCurrent();
		 //setPharmacyStorageItem(new PharmacyStorageItemDTO(itemPrice.getPharmacyStorageItem()));
		
	 }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	/*public PharmacyStorageItemDTO getPharmacyStorageItem() {
		return pharmacyStorageItemDTO;
	}

	public void setPharmacyStorageItem(PharmacyStorageItemDTO pharmacyStorageItemDTO) {
		this.pharmacyStorageItemDTO = pharmacyStorageItemDTO;
	}
	 */
	 
}
