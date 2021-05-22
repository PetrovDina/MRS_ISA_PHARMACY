package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import mrsisa12.pharmacy.dto.ItemPriceDTO;

@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)

@Entity
public class ItemPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "current", nullable = false)
	private boolean current;
	
	@Column(name = "promotion", nullable = false)
	private boolean promotion;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "timePeriod")
	private TimePeriod timePeriod;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHARMACYSTORAGEITEM_ID", referencedColumnName = "ID")
	private PharmacyStorageItem pharmacyStorageItem;

	public ItemPrice() {

	}

	public ItemPrice(double price, boolean current, boolean promotion, TimePeriod timePeriod) {
		super();
		this.price = price;
		this.current = current;
		this.promotion = promotion;
		this.timePeriod = timePeriod;
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

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public PharmacyStorageItem getPharmacyStorageItem() {
		return pharmacyStorageItem;
	}

	public void setPharmacyStorageItem(PharmacyStorageItem pharmacyStorageItem) {
		this.pharmacyStorageItem = pharmacyStorageItem;
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}
}
