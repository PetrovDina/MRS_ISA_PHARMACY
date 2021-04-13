package mrsisa12.pharmacy.dto;

import java.util.Date;

import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.ReservationStatus;

public class ReservationDTO {


    private Long id;
	private PatientDTO patient; //todo odkomentarisati kada kreiramo PatientDTO klasu + getteri i setteri
    private PharmacyDTO pharmacy;
	private MedicationDTO medication;
	private int quantity;
	private Date dueDate;
	private ReservationStatus status;

	
	public ReservationDTO() {
		super();
	}
	

	public ReservationDTO(Reservation r) {
		this.id = r.getId();
		this.patient = new PatientDTO(r.getPatient());
		this.medication = new MedicationDTO(r.getMedication());
		this.pharmacy = new PharmacyDTO(r.getPharmacy());
		this.quantity = r.getQuantity();
		this.dueDate = r.getDueDate();
		this.status = r.getStatus();
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


	public MedicationDTO getMedication() {
		return medication;
	}

	public void setMedication(MedicationDTO medication) {
		this.medication = medication;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}


	public PharmacyDTO getPharmacy() {
		return pharmacy;
	}


	public void setPharmacy(PharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}


	@Override
	public String toString() {
		return "ReservationDTO [id=" + id + ", patient=" + patient + ", pharmacy=" + pharmacy + ", medication="
				+ medication + ", quantity=" + quantity + ", dueDate=" + dueDate + ", status=" + status + "]";
	}
	
	

}