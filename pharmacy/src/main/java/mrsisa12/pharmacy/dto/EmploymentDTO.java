package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Employment;

public class EmploymentDTO {

	private Long id;
	private EmployeeDTO employee;
	private TimePeriodDTO workTime;
	private PharmacyDTO pharmacy;

	public EmploymentDTO() {}
	
	public EmploymentDTO(Long id, EmployeeDTO employee, TimePeriodDTO workTime, PharmacyDTO pharmacy) {
		super();
		this.id = id;
		this.employee = employee;
		this.workTime = workTime;
		this.pharmacy = pharmacy;
	}
	
	public EmploymentDTO(Employment employment) {
		this(employment.getId(), new EmployeeDTO(employment.getEmployee()), new TimePeriodDTO(employment.getWorkTime()), new PharmacyDTO(employment.getPharmacy()));
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public TimePeriodDTO getWorkTime() {
		return workTime;
	}

	public void setWorkTime(TimePeriodDTO workTime) {
		this.workTime = workTime;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
