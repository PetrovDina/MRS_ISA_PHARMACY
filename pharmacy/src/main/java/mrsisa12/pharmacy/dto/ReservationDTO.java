package mrsisa12.pharmacy.dto;

import java.util.Date;

import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.ReservationStatus;

public class ReservationDTO {


    private Long id;
//	private Patient patient; //todo odkomentarisati kada kreiramo Patient klasu + getteri i setteri
//	private List<ReservationItem> reservationItems; //ovo onda necemo imati?
	private Date dueDate;
	private ReservationStatus status;

	
	public ReservationDTO() {
		super();
	}
	
	public ReservationDTO(Long id, Date dueDate, ReservationStatus status) {
		//todo dodati i pacijenta ovde
		super();
		this.id = id;
//		this.reservationItems = reservationItems;
		this.dueDate = dueDate;
		this.status = status; 
	}


	public ReservationDTO(Reservation r) {
		this.id = r.getId();
		this.dueDate = r.getDueDate();
		this.status = r.getStatus();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
//
//	public List<ReservationItem> getReservationItems() {
//		return reservationItems;
//	}
//
//	public void setReservationItems(List<ReservationItem> reservationItems) {
//		this.reservationItems = reservationItems;
//	}

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

	
}


