package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Pharmacy;

public class PharmacyDTO {

	private Long id;
	private String name;
	private double rating;
	private Location location;
	
	public PharmacyDTO() {
	}
	
	public PharmacyDTO(Pharmacy pharmacy) {
		this.id = pharmacy.getId();
		this.name = pharmacy.getName();
		this.location = pharmacy.getLocation();
		this.rating = pharmacy.getRating();
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
