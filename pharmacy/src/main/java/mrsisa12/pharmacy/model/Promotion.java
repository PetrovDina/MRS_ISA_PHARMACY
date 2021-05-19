package mrsisa12.pharmacy.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@SQLDelete(sql = "UPDATE promotion " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "promotion")
	private List<PharmacyStorageItem> pharmacyStorageItems;

	@Column(name = "dueDate", nullable = false)
	private LocalDate dueDate;
	
	@Column(name = "deleted")
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<PharmacyStorageItem> getPharmacyStorageItems() {
		return pharmacyStorageItems;
	}

	public void setPharmacyStorageItems(List<PharmacyStorageItem> pharmacyStorageItems) {
		this.pharmacyStorageItems = pharmacyStorageItems;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}
