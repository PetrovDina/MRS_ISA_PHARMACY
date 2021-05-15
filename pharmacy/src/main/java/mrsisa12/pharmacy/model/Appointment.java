package mrsisa12.pharmacy.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;

@Entity
@SQLDelete(sql = "UPDATE appointment " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Appointment {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "appointmentStatus", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;
	
	@Column(name = "appointmentType", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private AppointmentType type;
	
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "timePeriod")
	private TimePeriod timePeriod;
	
	
	@Column(name="price", unique=false, nullable=false)
	private double price;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHARMACY_ID", referencedColumnName = "ID")
	private Pharmacy pharmacy;
	
	
//	private Report report;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "ID")
	private Patient patient;
	
	@Column(name = "deleted")
	private boolean deleted;
	
	@Column(name = "report")
	private String report;
	
	public Appointment() {}
	
	public Appointment(AppointmentStatus status, TimePeriod timePeriod, Employee employee, Patient patient, double price, Pharmacy pharmacy, AppointmentType type
) {
		super();
		this.status = status;
		this.timePeriod = timePeriod;
		this.employee = employee;
		this.patient = patient;
		this.price = price;
		this.pharmacy = pharmacy;
		this.type = type;
		this.report = "";
	}
	
	public Appointment(Appointment app) {
		this(app.getStatus(), app.getTimePeriod(), app.getEmployee(), app.getPatient(), app.getPrice(), app.getPharmacy(), app.getType());
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppointmentStatus getStatus() {
		return status;
	}

	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
	}
	
	
}
