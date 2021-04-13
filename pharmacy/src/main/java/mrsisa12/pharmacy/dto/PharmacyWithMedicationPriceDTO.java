package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;

public class PharmacyWithMedicationPriceDTO {

	private PharmacyDTO pharmacy;
	private int availableQuantity;
	private double currentPrice;
	
	
	public PharmacyWithMedicationPriceDTO() {
		super();
	}

	public PharmacyWithMedicationPriceDTO(PharmacyDTO pharmacy, int availableQuantity, double currentPrice) {
		
		super();
		this.pharmacy = pharmacy;
		this.availableQuantity = availableQuantity;
		this.currentPrice = currentPrice;
	}
	

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	public int getAvailableQuantity() {
		return availableQuantity;
	}
	public void setAvailableQuantity(int availableQuantity) {
		this.availableQuantity = availableQuantity;
	}
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	

}
