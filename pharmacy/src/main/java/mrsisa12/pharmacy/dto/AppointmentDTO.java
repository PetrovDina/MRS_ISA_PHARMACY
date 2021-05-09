package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;

public class AppointmentDTO {

	private Long id;
	private AppointmentStatus status;
	private TimePeriodDTO timePeriod;
	private double price;
//	private Report report;
	private EmployeeDTO employee;
	private PatientDTO patient;
	private PharmacyDTO pharmacy;
	private AppointmentType type;


	public AppointmentDTO() {
	}

	public AppointmentDTO(Long id, AppointmentStatus status, TimePeriodDTO timePeriod, EmployeeDTO employee,
			PatientDTO patient, double price, PharmacyDTO pharmacy, AppointmentType type) {
		super();
		this.id = id;
		this.status = status;
		this.timePeriod = timePeriod;
		this.employee = employee;
		this.patient = patient;
		this.price = price;
		this.pharmacy = pharmacy;
		this.type = type;
	}

	public AppointmentDTO(Appointment appointment) {
		this(appointment.getId(), appointment.getStatus(), new TimePeriodDTO(appointment.getTimePeriod()),
				new EmployeeDTO(appointment.getEmployee()),
				(appointment.getPatient() == null) ? null : new PatientDTO(appointment.getPatient()), appointment.getPrice(), new PharmacyDTO(appointment.getPharmacy()), appointment.getType());
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

	public TimePeriodDTO getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriodDTO timePeriod) {
		this.timePeriod = timePeriod;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public EmployeeDTO getEmployee() {
		return employee;
	}

	public void setEmployee(EmployeeDTO employee) {
		this.employee = employee;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

	public AppointmentType getType() {
		return type;
	}

	public void setType(AppointmentType type) {
		this.type = type;
	}

	
	
	

}
