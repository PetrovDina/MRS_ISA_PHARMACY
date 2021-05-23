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
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AbsenceType;

@Entity
public class Absence {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee employee;
	
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "timePeriod")
	private TimePeriod timePeriod;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHARMACY_ID", referencedColumnName = "ID")
	private Pharmacy pharmacy;
	
	@Column(name = "absenceStatus", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private AbsenceStatus status;
	
	@Column(name = "absenceType", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private AbsenceType type;

	public Absence(Long id, Employee employee, TimePeriod timePeriod, Pharmacy pharmacy, AbsenceStatus status,
			AbsenceType type) {
		super();
		this.id = id;
		this.employee = employee;
		this.timePeriod = timePeriod;
		this.pharmacy = pharmacy;
		this.status = status;
		this.type = type;
	}

	public Absence() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public AbsenceStatus getStatus() {
		return status;
	}

	public void setStatus(AbsenceStatus status) {
		this.status = status;
	}

	public AbsenceType getType() {
		return type;
	}

	public void setType(AbsenceType type) {
		this.type = type;
	}
	
	
	

}
