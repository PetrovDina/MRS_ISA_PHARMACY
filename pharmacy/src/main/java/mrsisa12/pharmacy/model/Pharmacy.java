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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Pharmacy {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@OneToOne
	private Location location;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="pharmacy")
	private List<PharmacyStorageItem>pharmacyStorageItems;

	public Pharmacy() {
		
	}
	
	public Pharmacy(Long id, String name, Location location) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
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
		if (!this.pharmacyStorageItems.contains(newpharmacyStorageItem))
		{
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
	   if (pharmacyStorageItems != null)
	   {
		   PharmacyStorageItem oldPharmacyStorageItem;
		   for (Iterator<PharmacyStorageItem> iter = getIteratorPharmacyStorageItem(); iter.hasNext();)
		   {
			   oldPharmacyStorageItem = iter.next();
			   iter.remove();
		   }
	   }
   }
	
}