package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AbsenceType;

public class AbsenceDTO {

	private Long id;
	private AbsenceStatus status;
	private TimePeriodDTO timePeriod;
	private EmployeeDTO employee;
	private PharmacyDTO pharmacy;
	private AbsenceType type;
	
	public AbsenceDTO() {
		super();
	}

	public AbsenceDTO(Long id, AbsenceStatus status, TimePeriodDTO timePeriod, EmployeeDTO employee,
			PharmacyDTO pharmacy, AbsenceType type) {
		super();
		this.id = id;
		this.status = status;
		this.timePeriod = timePeriod;
		this.employee = employee;
		this.pharmacy = pharmacy;
		this.type = type;
	}
	
	public AbsenceDTO(Absence absence) {
		this(absence.getId(), absence.getStatus(), new TimePeriodDTO(absence.getTimePeriod()), 
				new EmployeeDTO(absence.getEmployee()), new PharmacyDTO(absence.getPharmacy()), 
				absence.getType());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AbsenceStatus getStatus() {
		return status;
	}

	public void setStatus(AbsenceStatus status) {
		this.status = status;
	}

	public TimePeriodDTO getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriodDTO timePeriod) {
		this.timePeriod = timePeriod;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	public AbsenceType getType() {
		return type;
	}

	public void setType(AbsenceType type) {
		this.type = type;
	}
	
	
}
