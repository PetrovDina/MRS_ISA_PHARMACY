package mrsisa12.pharmacy.dto;

import java.util.Date;

import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.enums.EPrescriptionStatus;

public class TherapyDTO {
	private Long id;
	private PatientDTO patient; 
	private Date prescribedDate;
	private String code;
	private EPrescriptionStatus status;
	
	public TherapyDTO(Therapy ep) {
		super();
		this.id = ep.getId();
		this.patient = new PatientDTO(ep.getPatient());
		this.prescribedDate = ep.getPrescribedDate();
		this.code = ep.getCode();
		this.status = ep.getStatus();
	}

	public TherapyDTO() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PatientDTO getPatient() {
		return patient;
	}

	public void setPatient(PatientDTO patient) {
		this.patient = patient;
	}

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EPrescriptionStatus getStatus() {
		return status;
	}

	public void setStatus(EPrescriptionStatus status) {
		this.status = status;
	}
	
	
	
}
