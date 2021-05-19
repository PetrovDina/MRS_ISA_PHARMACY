package mrsisa12.pharmacy.dto.pharmacy;

import mrsisa12.pharmacy.dto.LocationDTO;
import mrsisa12.pharmacy.model.AppointmentPriceCatalog;
import mrsisa12.pharmacy.model.Pharmacy;

public class PharmacyDTO {

	private Long id;
	private String name;
	private double rating;
	private LocationDTO location;
	private AppointmentPriceCatalog appointmentPriceCatalog;

	public PharmacyDTO() {
	}

	public PharmacyDTO(Long id, String name, double rating, LocationDTO location,
			AppointmentPriceCatalog appointmentPriceCatalog) {
		this();
		this.id = id;
		this.name = name;
		this.rating = rating;
		this.location = location;
		this.appointmentPriceCatalog = appointmentPriceCatalog;
	}

	public PharmacyDTO(Pharmacy pharmacy) {
		this(pharmacy.getId(), pharmacy.getName(), pharmacy.getRating(), new LocationDTO(pharmacy.getLocation()),
				pharmacy.getAppointmentPriceCatalog());
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

	public LocationDTO getLocation() {
		return location;
	}

	public void setLocation(LocationDTO location) {
		this.location = location;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public AppointmentPriceCatalog getAppointmentPriceCatalog() {
		return appointmentPriceCatalog;
	}

	public void setAppointmentPriceCatalog(AppointmentPriceCatalog appointmentPriceCatalog) {
		this.appointmentPriceCatalog = appointmentPriceCatalog;
	}

	@Override
	public String toString() {
		return "PharmacyDTO [id=" + id + ", name=" + name + ", rating=" + rating + ", location=" + location + "]";
	}

}
