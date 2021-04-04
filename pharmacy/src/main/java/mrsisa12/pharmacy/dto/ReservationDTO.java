package mrsisa12.pharmacy.dto;

import java.util.Date;

import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.ReservationStatus;

public class ReservationDTO {


    private Long id;
//	private PatientDTO patient; //todo odkomentarisati kada kreiramo PatientDTO klasu + getteri i setteri
    private PharmacyDTO pharmacy;
	private MedicationDTO medication;
	private String quantity;
	private Date dueDate;
	private ReservationStatus status;

	
	public ReservationDTO() {
		super();
	}
	

	public ReservationDTO(Reservation r) {
		this.id = r.getId();
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

	public MedicationDTO getMedication() {
		return medication;
	}

	public void setMedication(MedicationDTO medication) {
		this.medication = medication;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
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
	
	

}