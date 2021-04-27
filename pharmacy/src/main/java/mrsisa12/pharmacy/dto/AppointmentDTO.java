package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;

public class AppointmentDTO {

	private Long id;
	private AppointmentStatus status;
	private TimePeriodDTO timePeriod;
//	@OneToOne()
//	private Pharmacy pharmacy;
//	private Report report;
	private EmployeeDTO employee;
	private PatientDTO patient;

	public AppointmentDTO() {
	}

	public AppointmentDTO(Long id, AppointmentStatus status, TimePeriodDTO timePeriod, EmployeeDTO employee,
			PatientDTO patient) {
		super();
		this.id = id;
		this.status = status;
		this.timePeriod = timePeriod;
		this.employee = employee;
		this.patient = patient;
	}

	public AppointmentDTO(Appointment appointment) {
		this(appointment.getId(), appointment.getStatus(), new TimePeriodDTO(appointment.getTimePeriod()),
				new EmployeeDTO(appointment.getEmployee()),
				(appointment.getPatient() == null) ? null : new PatientDTO(appointment.getPatient()));
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

	public TimePeriodDTO getTimePeriodDTO() {
		return timePeriod;
	}

	public void setTimePeriodDTO(TimePeriodDTO timePeriod) {
		this.timePeriod = timePeriod;
	}

	public EmployeeDTO getEmployeeDTO() {
		return employee;
	}

	public void setEmployeeDTO(EmployeeDTO employee) {
		this.employee = employee;
	}

	public PatientDTO getPatientDTO() {
		return patient;
	}

	public void setPatientDTO(PatientDTO patient) {
		this.patient = patient;
	}

}
