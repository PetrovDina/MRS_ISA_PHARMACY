package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;

public class PharmacyWithMedicationsPriceQrDTO {
	
	private PharmacyDTO pharmacy;
	private double price;
	
	public PharmacyWithMedicationsPriceQrDTO() { }
	
	public PharmacyWithMedicationsPriceQrDTO(PharmacyDTO pharmacy, double price) {
		super();
		this.pharmacy = pharmacy;
		this.price = price;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
