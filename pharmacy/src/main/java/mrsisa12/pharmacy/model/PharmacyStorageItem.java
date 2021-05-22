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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE pharmacystorageitem " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")
public class PharmacyStorageItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "pharmacyStorageItem")
	private List<ItemPrice> itemPrices;

	@ManyToOne(optional = false)
	private Medication medication;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHARMACY_ID", referencedColumnName = "ID")
	private Pharmacy pharmacy;

	@ManyToOne(fetch = FetchType.EAGER)
	private Promotion promotion;
	
	@Column(name = "deleted")
	private boolean deleted;

	public PharmacyStorageItem() {
	}

	public PharmacyStorageItem(Long id, Medication medication, int quantity, Pharmacy pharmacy) {
		super();
		this.id = id;
		this.medication = medication;
		this.quantity = quantity;
		this.pharmacy = pharmacy;
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

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public void setItemPrices(List<ItemPrice> itemPrices) {
		this.itemPrices = itemPrices;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Promotion getPromotion() {
		return promotion;
	}

	public void setPromotion(Promotion promotion) {
		this.promotion = promotion;
	}
}
