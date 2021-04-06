package mrsisa12.pharmacy.dto;

import java.util.ArrayList;
import java.util.List;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;

public class PharmacyDTO {

	private Long id;
	private String name;
	private double rating;
	private Location location;
	private List<PharmacyStorageItemDTO> pharmacyStorageItems;
	
	public PharmacyDTO() {
		this.pharmacyStorageItems = new ArrayList<PharmacyStorageItemDTO>();
	}
	
	public PharmacyDTO(Pharmacy pharmacy, String type) {
		this();
		this.id = pharmacy.getId();
		this.name = pharmacy.getName();
		this.location = pharmacy.getLocation();
		this.rating = pharmacy.getRating();
		if(type.equals("sa"))
		fillStorageItems(pharmacy.getPharmacyStorageItems());
	}
	

	private void fillStorageItems(List<PharmacyStorageItem> pSItems) {
		for(PharmacyStorageItem psi : pSItems) {
			this.pharmacyStorageItems.add(new PharmacyStorageItemDTO(psi));
		}
	}

	public PharmacyDTO(Long id, String name, Location location, double rating) {
		this.id = id;
		this.name = name;
		this.location = location;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	
}
