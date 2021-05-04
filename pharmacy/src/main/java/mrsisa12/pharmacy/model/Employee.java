package mrsisa12.pharmacy.model;

import static javax.persistence.InheritanceType.JOINED;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
@Table(name = "employees")
@Inheritance(strategy=JOINED)
public abstract class Employee extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Column(name = "rating", nullable = false)
	private double rating;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "employee")
	private List<Appointment> appointments;

	public Employee() {}

	public Employee(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, double rating) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
		this.rating = rating;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	public void addAppointment(Appointment appointment) {
		if (appointment == null)
			return;
		if (this.appointments == null)
			this.appointments = new ArrayList<>();
		if (!this.appointments.contains(appointment)) {
			this.appointments.add(appointment);
		}
	}
	
}
