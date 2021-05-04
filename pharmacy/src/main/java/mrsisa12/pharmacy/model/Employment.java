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

import mrsisa12.pharmacy.model.enums.EmploymentContractType;
import mrsisa12.pharmacy.model.enums.MedicationForm;

@Entity
@SQLDelete(sql = "UPDATE employment " + "SET deleted = true " + "WHERE id = ?")
@Where(clause = "deleted = false")
public class Employment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "EMPLOYEE_ID", referencedColumnName = "ID")
	private Employee employee;

	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb", name = "workTime")
	private TimePeriod workTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "contractType", nullable = false)
	private EmploymentContractType contractType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PHARMACY_ID", referencedColumnName = "ID")
	private Pharmacy pharmacy;

	@Column(name = "deleted")
	private boolean deleted;

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

	public TimePeriod getWorkTime() {
		return workTime;
	}

	public void setWorkTime(TimePeriod workTime) {
		this.workTime = workTime;
	}

	public EmploymentContractType getContractType() {
		return contractType;
	}

	public void setContractType(EmploymentContractType contractType) {
		this.contractType = contractType;
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
}
