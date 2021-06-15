package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;

public class LocationDTO {
	
	private Long id;
	private Double latitude;
	private Double longitude;
	private String street;
	private String city;
	private String zipcode;
	private Integer streetNum;
	
	public LocationDTO() {
		
	}
	
	public LocationDTO(Location location) {
		this(location.getId(), location.getLatitude(), location.getLongitude(), location.getStreet(), location.getCity(), location.getZipcode(), location.getStreetNum());
	}

	public LocationDTO(Long id, Double latitude, Double longitude, String street, String city, String zipcode,
			Integer streetNum) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.street = street;
		this.city = city;
		this.zipcode = zipcode;
		this.streetNum = streetNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Integer getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(Integer streetNum) {
		this.streetNum = streetNum;
	}
	
}
