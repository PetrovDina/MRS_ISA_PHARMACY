package mrsisa12.pharmacy.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
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
	private Patient patient;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	private Pharmacy pharmacy;

	@ManyToOne(optional = false)
	private Medication medication;

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "medicationPrice", nullable = false)
	private double medicationPrice;

	@Column(name = "dueDate", nullable = false)
	private Date dueDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

	@Column(name = "code", nullable = false, length = 10)
	private String code;

	public Reservation() {
		super();
	}

	public Reservation(Long id, Patient pat, Pharmacy ph, Medication med, int quanity, double medicationPrice,
			Date dueDate, ReservationStatus status, String code) {
		super();
		this.patient = pat;
		this.id = id;
		this.pharmacy = ph;
		this.medication = med;
		this.quantity = quanity;
		this.medicationPrice = medicationPrice;
		this.dueDate = dueDate;
		this.status = status;
		this.code = code;
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

	public double getMedicationPrice() {
		return medicationPrice;
	}

	public void setMedicationPrice(double medicationPrice) {
		this.medicationPrice = medicationPrice;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void update(Reservation reservation) {
		this.id = reservation.getId();
		this.patient = reservation.getPatient();
		this.pharmacy = reservation.getPharmacy();
		this.medication = reservation.getMedication();
		this.quantity = reservation.getQuantity();
		this.dueDate = reservation.getDueDate();
		this.status = reservation.getStatus();
	}

	@Override
	public String toString() {
		return "Reservation [id=" + id + ", patient=" + patient + ", pharmacy=" + pharmacy + ", medication="
				+ medication + ", quantity=" + quantity + ", dueDate=" + dueDate + ", status=" + status + ", getId()="
				+ getId() + ", getPatient()=" + getPatient() + ", getMedication()=" + getMedication()
				+ ", getQuantity()=" + getQuantity() + ", getDueDate()=" + getDueDate() + ", getStatus()=" + getStatus()
				+ ", getPharmacy()=" + getPharmacy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}