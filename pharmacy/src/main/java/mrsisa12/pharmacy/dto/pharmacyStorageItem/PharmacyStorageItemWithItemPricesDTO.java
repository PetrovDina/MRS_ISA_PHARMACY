package mrsisa12.pharmacy.dto.pharmacyStorageItem;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.ItemPriceDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyStorageItemWithItemPricesDTO extends PharmacyStorageItemDTO {

	private List<ItemPriceDTO> itemPrices;

	public PharmacyStorageItemWithItemPricesDTO() {
	}

	public PharmacyStorageItemWithItemPricesDTO(PharmacyStorageItem pharmacyStorageItem) {
		super(pharmacyStorageItem);
		fillItemPrices(pharmacyStorageItem.getItemPrices());
	}

	private void fillItemPrices(List<ItemPrice> itemPricesList) {
		if (this.itemPrices == null) {
			this.itemPrices = new ArrayList<ItemPriceDTO>();
		}
		for (ItemPrice ip : itemPricesList) {
			this.itemPrices.add(new ItemPriceDTO(ip));
		}
	}

	public List<ItemPriceDTO> getItemPrices() {
		return itemPrices;
	}

	public void setItemPrices(List<ItemPriceDTO> itemPrices) {
		this.itemPrices = itemPrices;
	}

}
