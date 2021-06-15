
package mrsisa12.pharmacy.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("medication_rating")
public class MedicationRating extends Rating {

	@ManyToOne
	private Medication medication;

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}


	
	
	
}
