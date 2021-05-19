package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Location {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "latitude", nullable = false)
	private double latitude;
	
	@Column(name = "longitude", nullable = false)
	private double longitude;
	
	@Column(name = "street", nullable = false)
	private String street;
	
	@Column(name = "city", nullable = false)
	private String city;
	
	@Column(name = "zipcode", nullable = false)
	private String zipcode;
	
	@Column(name = "streetNum", nullable = false)
	private int streetNum;
	
	public Location() {
		
	}

	public Location(double latitude, double longitude, String street, String city, String zipcode, int streetNum) {
		super();
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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
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

	public int getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(int streetNum) {
		this.streetNum = streetNum;
	}
	
	
}
