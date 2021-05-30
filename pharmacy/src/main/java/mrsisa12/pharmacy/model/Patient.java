package mrsisa12.pharmacy.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.PatientCategory;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
public class Patient extends User {


	private static final long serialVersionUID = 1L;
	
	@Column(name = "penaltyPoints", unique=false, nullable=false)
	Integer penaltyPoints;
	
	@Column(name = "loyaltyPoints", unique=false, nullable=false)
	Integer loyaltyPoints;
	
	@Column(name = "category", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private PatientCategory category;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "allergies",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"))
    private List<Medication> allergies;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Complaint> complaints;
	

	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "subscriptions",
            joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "pharmacy_id", referencedColumnName = "id"))
    private List<Pharmacy> subscriptions;
	
	@OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Therapy> ePrescriptions;
	
	public Patient() { }

	public Patient(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, Integer penaltyPoints, 
			List<Complaint> complaints) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
		this.penaltyPoints = penaltyPoints;
		this.complaints = complaints;
	}
	
	public Patient(Long id, String username, String password, String email, String firstName, String lastName,
			Location location, Gender gender, UserStatus activeStatus, List<UserRole> userRoles, boolean deleted, Integer penaltyPoints, 
			List<Complaint> complaints, Integer loyaltyPoints) {
		super(id, username, password, email, firstName, lastName, location, gender, activeStatus, userRoles, deleted);
		this.penaltyPoints = penaltyPoints;
		this.complaints = complaints;
		this.loyaltyPoints = loyaltyPoints;
	}

	public List<Medication> getAllergies() {
		return allergies;
	}

	public List<Pharmacy> getSubscriptions() {
		return subscriptions;
	}

	public void setSubscriptions(List<Pharmacy> subscriptions) {
		this.subscriptions = subscriptions;
	}

	public void setAllergies(List<Medication> allergies) {
		this.allergies = allergies;
	}

	public Integer getPenaltyPoints() {
		return penaltyPoints;
	}

	public void setPenaltyPoints(Integer penaltyPoints) {
		this.penaltyPoints = penaltyPoints;
	}

	public List<Complaint> getComplaints() {
		return complaints;
	}

	public void setComplaints(List<Complaint> complaints) {
		this.complaints = complaints;
	}

	public boolean removeSubscription(Long subId) {
	for (Pharmacy p : subscriptions) {
		if (p.getId() == subId) {
			subscriptions.remove(p);
			return true;
		}
		
	}
	return false;
	}

	public List<Therapy> getEPrescriptions() {
		return ePrescriptions;
	}

	public void setEPrescriptions(List<Therapy> ePrescriptions) {
		this.ePrescriptions = ePrescriptions;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public PatientCategory getCategory() {
		return category;
	}

	public void setCategory(PatientCategory category) {
		this.category = category;
	}
	
}
