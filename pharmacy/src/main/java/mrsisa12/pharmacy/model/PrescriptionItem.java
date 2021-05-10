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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "APPOINTMENT_ID", referencedColumnName = "ID")
	private Appointment appointment;
	
	public PrescriptionItem() {
		
	}

	public PrescriptionItem(Long id, Medication medication, int quantity, int therapyDuration, Appointment appointment) {
		super();
		this.id = id;
		this.medication = medication;
		this.quantity = quantity;
		this.therapyDuration = therapyDuration;
		this.appointment = appointment;
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

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	
	
}
