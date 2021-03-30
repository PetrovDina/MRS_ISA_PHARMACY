package mrsisa12.pharmacy.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PharmacyStorageItem {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ItemPrice> itemPrices;
	
	@ManyToOne(optional=false)
	private Medication medication;
	/*
	@ManyToOne(fetch = FetchType.LAZY)
	private Pharmacy pharmacy;*/

	public PharmacyStorageItem() {
		
	}

	public PharmacyStorageItem(Long id, Medication medication, int quantity) {
		super();
		this.id = id;
		this.medication = medication;
		this.quantity = quantity;
	}

	public Medication getMedication() {
	      return medication;
   }

   public void setMedication(Medication medication) {
      this.medication = medication;
   }
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public List<ItemPrice> getItemPrices() {
	      if (itemPrices == null)
	    	  itemPrices = new ArrayList<>();
	      return itemPrices;
	}

	public void addItemPrice(ItemPrice newItemPrice) {
	      if (newItemPrice == null)
	         return;
	      if (this.itemPrices == null)
	         this.itemPrices = new ArrayList<>();
	      this.itemPrices.add(newItemPrice);
	}
	
	public void removeItemPrice(ItemPrice oldItemPrice) {
	      if (oldItemPrice == null)
	         return;
	      if (this.itemPrices != null && this.itemPrices.contains(oldItemPrice)) {
	         this.itemPrices.remove(oldItemPrice);
	      }
	}

	/*public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}*/

	public void setItemPrices(List<ItemPrice> itemPrices) {
		this.itemPrices = itemPrices;
	}
	
}
