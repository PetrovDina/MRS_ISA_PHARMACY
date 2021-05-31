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
public class EPrescriptionItem {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@ManyToOne(optional=false)
	private Medication medication;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "e_prescription_id", referencedColumnName = "id")
	private EPrescription ePrescription;
	
	public EPrescriptionItem() { }

	public EPrescriptionItem(int quantity, Medication medication, EPrescription ePrescription) {
		super();
		this.quantity = quantity;
		this.medication = medication;
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

	public EPrescription getePrescription() {
		return ePrescription;
	}

	public void setePrescription(EPrescription ePrescription) {
		this.ePrescription = ePrescription;
	}
	
}
