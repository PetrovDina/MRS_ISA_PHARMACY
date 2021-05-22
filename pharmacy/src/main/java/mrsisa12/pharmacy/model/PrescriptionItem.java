package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class PrescriptionItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@ManyToOne(optional=false)
	private Medication medication;
	
	@Column(name = "therapyDuration", nullable = false)
	private int therapyDuration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EPRESCRIPTION_ID", referencedColumnName = "ID")
	private EPrescription ePrescription;
	
	public PrescriptionItem() {
		
	}


	public PrescriptionItem(Long id, int quantity, Medication medication, int therapyDuration,
			EPrescription ePrescription) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.medication = medication;
		this.therapyDuration = therapyDuration;
		this.ePrescription = ePrescription;
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

	public Medication getMedication() {
		return medication;
	}

	public void setMedication(Medication medication) {
		this.medication = medication;
	}

	public int getTherapyDuration() {
		return therapyDuration;
	}

	public void setTherapyDuration(int therapyDuration) {
		this.therapyDuration = therapyDuration;
	}

	public EPrescription getEPrescription() {
		return ePrescription;
	}


	public void setEPrescription(EPrescription ePrescription) {
		this.ePrescription = ePrescription;
	}

	
	
}
