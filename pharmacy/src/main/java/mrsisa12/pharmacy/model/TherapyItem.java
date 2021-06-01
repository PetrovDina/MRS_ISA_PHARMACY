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
public class TherapyItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "medicationPrice", nullable = false)
	private double medicationPrice;
	
	@ManyToOne(optional=false)
	private Medication medication;
	
	@Column(name = "therapyDuration", nullable = false)
	private int therapyDuration;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "THERAPY_ID", referencedColumnName = "ID")
	private Therapy ePrescription;
	
	public TherapyItem() {
		
	}


	public TherapyItem(Long id, int quantity, Medication medication, int therapyDuration,
			Therapy ePrescription, double medicationPrice) {
		super();
		this.id = id;
		this.quantity = quantity;
		this.medication = medication;
		this.therapyDuration = therapyDuration;
		this.ePrescription = ePrescription;
		this.medicationPrice = medicationPrice;
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

	public Therapy getEPrescription() {
		return ePrescription;
	}


	public void setEPrescription(Therapy ePrescription) {
		this.ePrescription = ePrescription;
	}


	public double getMedicationPrice() {
		return medicationPrice;
	}


	public void setMedicationPrice(double medicationPrice) {
		this.medicationPrice = medicationPrice;
	}

	
	
}
