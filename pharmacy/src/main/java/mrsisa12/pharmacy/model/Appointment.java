package mrsisa12.pharmacy.model;

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

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import mrsisa12.pharmacy.model.enums.AppointmentStatus;

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
	
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "timePeriod")
	private TimePeriod timePeriod;
	
//	@OneToOne()
//	private Pharmacy pharmacy;
//	private Report report;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee employee;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "ID")
	private Patient patient;
	
	@Column(name = "deleted")
	private boolean deleted;

	public Appointment() {}
	
	public Appointment(AppointmentStatus status, TimePeriod timePeriod, Employee employee, Patient patient) {
		super();
		this.status = status;
		this.timePeriod = timePeriod;
		this.employee = employee;
		this.patient = patient;
	}
	
	public Appointment(Appointment app) {
		this(app.getStatus(), app.getTimePeriod(), app.getEmployee(), app.getPatient());
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
