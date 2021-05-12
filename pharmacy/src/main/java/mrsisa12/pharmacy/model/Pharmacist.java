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

	public Employment getEmployment() {
		return employment;
	}

	public void setEmployment(Employment employment) {
		this.employment = employment;
	}

}
