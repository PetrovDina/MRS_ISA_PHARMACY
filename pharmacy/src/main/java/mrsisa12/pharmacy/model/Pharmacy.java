package mrsisa12.pharmacy.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pharmacy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "rating")
	private double rating;

	@OneToOne
	private Location location;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pharmacy")
	private List<PharmacyStorageItem> pharmacyStorageItems;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pharmacy")
	private List<Employment> employments;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<PharmacyAdmin> pharmacyAdmins;
	
	@OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Order> orders;

	public Pharmacy() {

	}

	public Pharmacy(Long id, String name, Location location, double rating) {
		super();
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

	public Iterator<PharmacyStorageItem> getIteratorPharmacyStorageItem() {
		if (pharmacyStorageItems == null)
			pharmacyStorageItems = new ArrayList<>();
		return pharmacyStorageItems.iterator();
	}

	public List<PharmacyStorageItem> getPharmacyStorageItems() {
		if (pharmacyStorageItems == null)
			pharmacyStorageItems = new ArrayList<>();
		return pharmacyStorageItems;
	}

	public void setPharmacyStorageItems(List<PharmacyStorageItem> newPharmacyStorageItem) {
		removeAllPharmacyStorageItems();
		for (Iterator<PharmacyStorageItem> iter = newPharmacyStorageItem.iterator(); iter.hasNext();)
			addPharmacyStorageItem(iter.next());
	}

	public void addPharmacyStorageItem(PharmacyStorageItem newpharmacyStorageItem) {
		if (newpharmacyStorageItem == null)
			return;
		if (this.pharmacyStorageItems == null)
			this.pharmacyStorageItems = new ArrayList<>();
		if (!this.pharmacyStorageItems.contains(newpharmacyStorageItem)) {
			this.pharmacyStorageItems.add(newpharmacyStorageItem);
		}
	}

	public void removePharmacyStorageItem(PharmacyStorageItem oldPharmacyStorageItem) {
		if (oldPharmacyStorageItem == null)
			return;
		if (this.pharmacyStorageItems != null && this.pharmacyStorageItems.contains(oldPharmacyStorageItem)) {
			this.pharmacyStorageItems.remove(oldPharmacyStorageItem);
		}
	}

	public void removeAllPharmacyStorageItems() {
		if (pharmacyStorageItems != null) {
			PharmacyStorageItem oldPharmacyStorageItem;
			for (Iterator<PharmacyStorageItem> iter = getIteratorPharmacyStorageItem(); iter.hasNext();) {
				oldPharmacyStorageItem = iter.next();
				iter.remove();
			}
		}
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Employment> getEmployments() {
		return employments;
	}

	public void setEmployments(List<Employment> employments) {
		this.employments = employments;
	}

	public List<PharmacyAdmin> getPharmacyAdmins() {
		return pharmacyAdmins;
	}

	public void setPharmacyAdmins(List<PharmacyAdmin> pharmacyAdmins) {
		this.pharmacyAdmins = pharmacyAdmins;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
