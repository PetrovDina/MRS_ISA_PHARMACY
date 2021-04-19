package mrsisa12.pharmacy.dto;

import java.util.Date;

import mrsisa12.pharmacy.model.Reservation;

public class ReservationPickupDTO {

	private String patientFirstName;
    private String patientLastName;
    private String patientEmail;
    private String medicationName;
    private Date pickUpDate;
    private String dateAsString;
    private String id;
    private String status;
    private int quantity;
    private boolean valid;

    public ReservationPickupDTO(Reservation reservation){
        this.patientEmail = reservation.getPatient().getEmail();
        this.patientFirstName = reservation.getPatient().getFirstName();
        this.patientLastName = reservation.getPatient().getLastName();
        this.setMedicationName(reservation.getMedication().getName());
        this.pickUpDate = reservation.getDueDate();
        this.setDateAsString(reservation.getDueDate().toString());
        this.id = reservation.getId().toString();
        this.status = reservation.getStatus().toString();
        this.setQuantity(reservation.getQuantity());
        checkValidity();
    }

    public ReservationPickupDTO(){
    }

    
    private void checkValidity() {
    	Date d = new Date();
        if(this.pickUpDate.before(new Date(System.currentTimeMillis() + 3600 * 24000)))
            this.valid = false;
        else
            this.valid = true;
    }

    public String getPatientFirstName() {
        return patientFirstName;
    }

    public void setPatientFirstName(String patientFirstName) {
        this.patientFirstName = patientFirstName;
    }

    public String getPatientLastName() {
        return patientLastName;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public void setPatientLastName(String patientLastName) {
        this.patientLastName = patientLastName;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public Date getPickUpDate() {
        return pickUpDate;
    }

    public void setPickUpDate(Date pickUpDate) {
        this.pickUpDate = pickUpDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	public String getMedicationName() {
		return medicationName;
	}

	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public String getDateAsString() {
		return dateAsString;
	}

	public void setDateAsString(String dateAsString) {
		this.dateAsString = dateAsString;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
