package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.Entity;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Supplier extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Supplier() 
	{
		super();
	}

	public Supplier(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted) 
	{
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);

	}

}
