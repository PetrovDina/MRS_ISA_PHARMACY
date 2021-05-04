package mrsisa12.pharmacy.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Pharmacist extends Employee {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	private Employment employment;
}
