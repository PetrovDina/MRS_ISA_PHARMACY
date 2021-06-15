package mrsisa12.pharmacy.dto.pharmacy;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Pharmacy;

public class PlainPharmacyDTO {
	
	private Long id;
	private String name;
	private double rating;
	private Location location;
	
	public PlainPharmacyDTO() { }
	
	public PlainPharmacyDTO(Long id, String name, double rating, Location location) {
		super();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.location = location;
	}
	
	public PlainPharmacyDTO(Pharmacy pharmacy) {
		this(pharmacy.getId(), pharmacy.getName(), pharmacy.getRating(), pharmacy.getLocation());
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}
