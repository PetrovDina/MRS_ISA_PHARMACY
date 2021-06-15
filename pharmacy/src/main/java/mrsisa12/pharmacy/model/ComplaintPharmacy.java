package mrsisa12.pharmacy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ComplaintPharmacy extends Complaint {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pharmacy_id", referencedColumnName = "id")
	private Pharmacy pharmacy;
	
	public ComplaintPharmacy() { }

	public ComplaintPharmacy(Long id, Patient patient, SystemAdmin systemAdmin, String content, String response, Pharmacy pharmacy) {
		super(id, patient, systemAdmin, content, response);
		this.pharmacy = pharmacy;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
}
