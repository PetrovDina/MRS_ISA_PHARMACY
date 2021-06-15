package mrsisa12.pharmacy.dto;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Promotion;

public class PromotionDTO {

	private Long id;
	private String dueDate;
	private List<PharmacyStorageItemWithItemPricesDTO> promotionItems;
	
	public PromotionDTO() {}

	public PromotionDTO(Promotion promotion) {
		super();
		this.id = promotion.getId();
		this.dueDate = (promotion.getDueDate() == null) ? null : promotion.getDueDate().toString();
		fillPromotionItems(promotion.getPharmacyStorageItems());
	}
	
	private void fillPromotionItems(List<PharmacyStorageItem> promotionItems) {
		if (this.promotionItems == null)
			this.promotionItems = new ArrayList<PharmacyStorageItemWithItemPricesDTO>();
		for (PharmacyStorageItem promotionItem : promotionItems)
			this.promotionItems.add(new PharmacyStorageItemWithItemPricesDTO(promotionItem));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PharmacyStorageItemWithItemPricesDTO> getPromotionItems() {
		return promotionItems;
	}

	public void setPromotionItems(List<PharmacyStorageItemWithItemPricesDTO> promotionItems) {
		this.promotionItems = promotionItems;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
}
