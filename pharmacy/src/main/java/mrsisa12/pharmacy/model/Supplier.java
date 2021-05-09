package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Supplier extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "supplier")
	private List<SupplierStorageItem> supplierStorageItems;

	public Supplier() 
	{
		super();
	}

	public Supplier(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, List<SupplierStorageItem> supplierStorageItems) 
	{
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
		this.supplierStorageItems = supplierStorageItems;
	}

	public List<SupplierStorageItem> getSupplierStorageItems() {
		return supplierStorageItems;
	}

	public void setSupplierStorageItems(List<SupplierStorageItem> supplierStorageItems) {
		this.supplierStorageItems = supplierStorageItems;
	}


}
