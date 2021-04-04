package mrsisa12.pharmacy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reservation {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
//	@OneToOne
//	@Column(name = "patient", nullable = false)
//	private Patient patient; //todo odkomentarisati kada kreiramo Patient klasu + getteri i setteri
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Column(name = "reservationItems", nullable = false)
	private List<ReservationItem> reservationItems;
	
	@Column(name = "dueDate", nullable = false)
	private Date dueDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private ReservationStatus status;

	
	public Reservation() {
		super();
	}
	
	
	public Reservation(Long id, List<ReservationItem> reservationItems, Date dueDate, ReservationStatus status) {
		//todo dodati i pacijenta ovde
		super();
		this.id = id;
		this.reservationItems = reservationItems;
		this.dueDate = dueDate;
		this.status = status;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ReservationItem> getReservationItems() {
		return reservationItems;
	}

	public void setReservationItems(List<ReservationItem> reservationItems) {
		this.reservationItems = reservationItems;
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

	
	
}
