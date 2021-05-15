package mrsisa12.pharmacy.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ComplaintEmployee extends Complaint {
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", referencedColumnName = "id")
	private Employee employee;

	public ComplaintEmployee() {
	}

	public ComplaintEmployee(Long id, Patient patient, SystemAdmin systemAdmin, String content, String response, Employee employee) {
		super(id, patient, systemAdmin, content, response);
		this.employee = employee;
	}

	public ComplaintEmployee(Employee employee) {
		super();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}
