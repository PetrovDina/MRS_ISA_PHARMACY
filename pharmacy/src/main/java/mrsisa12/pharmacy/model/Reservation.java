package mrsisa12.pharmacy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import mrsisa12.pharmacy.model.enums.ReservationStatus;

@Entity
public class Reservation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@OneToOne
	private Patient patient; //todo odkomentarisati kada kreiramo Patient klasu + getteri i setteri

	@ManyToOne(optional=false)
	private Pharmacy pharmacy;	
	
	@ManyToOne(optional=false)
	private Medication medication;	
	
	@Column(name = "quantity", nullable = false)
	private int quantity;
	
	@Column(name = "dueDate", nullable = false)
	private Date dueDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

	
	public Reservation() {
		super();
	}
	
	
	public Reservation(Long id, Patient pat, Pharmacy ph, Medication med, int quanity, Date dueDate, ReservationStatus status) {
		super();
		this.patient = pat;
		this.id = id;
		this.pharmacy = ph;
		this.medication = med;
		this.quantity = quanity;
		this.dueDate = dueDate;
		this.status = status;
	}


	public Long getId() {
		return id;
	}

	

	public Patient getPatient() {
		return patient;
	}


	public void setPatient(Patient patient) {
		this.patient = patient;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Medication getMedication() {
		return medication;
	}


	public void setMedication(Medication medication) {
		this.medication = medication;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Date getDueDate() {
		return dueDate;
	}


	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}


	public ReservationStatus getStatus() {
		return status;
	}


	public void setStatus(ReservationStatus status) {
		this.status = status;
	}


	public Pharmacy getPharmacy() {
		return pharmacy;
	}


	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
	

}