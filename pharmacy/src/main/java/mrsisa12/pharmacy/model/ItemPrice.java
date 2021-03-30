package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ItemPrice {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "current", nullable = false)
	private boolean current;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	private PharmacyStorageItem pharmacyStorageItem;*/

	public ItemPrice() {
		
	}

	public ItemPrice(Long id, double price, boolean current) {
		super();
		this.id = id;
		this.price = price;
		this.current = current;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	/*public PharmacyStorageItem getPharmacyStorageItem() {
		return pharmacyStorageItem;
	}

	public void setPharmacyStorageItem(PharmacyStorageItem pharmacyStorageItem) {
		this.pharmacyStorageItem = pharmacyStorageItem;
	}*/
	
	

}
