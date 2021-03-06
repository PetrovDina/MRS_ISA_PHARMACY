package mrsisa12.pharmacy.model;

import java.util.ArrayList;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.model.enums.MedicationForm;

@Entity
public class Medication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "manufacturer", nullable = false)
	private String manufacturer;

	@Column(name = "prescriptionReq", nullable = false)
	private boolean prescriptionReq;
	
	@Column(name = "rating", nullable = false)
	private double rating;

	@Enumerated(EnumType.STRING)
	@Column(name = "form", nullable = false)
	private MedicationForm form;
	
	@Column(name = "loyaltyPoints", nullable = false)
	private Integer loyaltyPoints;

	@ManyToMany
	@JoinTable(name = "alternative_medications", joinColumns = @JoinColumn(name = "medication_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "alternative_id", referencedColumnName = "id"))
	private List<Medication> alternatives;

//	@ManyToMany(mappedBy = "allergies")
//    private List<Patient> allergicPatients;
	
//	public List<Patient> getAllergicPatients() {
//		return allergicPatients;
//	}
//
//	public void setAllergicPatients(List<Patient> allergicPatients) {
//		this.allergicPatients = allergicPatients;
//	}

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "content", nullable = false)
	private String content;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "medication")
	private List<OrderItem> orderItems;

	public Medication() {

	}

	public Medication(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.prescriptionReq = prescriptionReq;
		this.form = form;
	}

	public Medication(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form,
			List<Medication> alternatives, String description, String content) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.prescriptionReq = prescriptionReq;
		this.form = form;
		this.alternatives = alternatives;
		this.description = description;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void copyValues(Medication medication) {
		this.name = medication.getName();

	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public boolean isPrescriptionReq() {
		return prescriptionReq;
	}

	public void setPrescriptionReq(boolean prescriptionReq) {
		this.prescriptionReq = prescriptionReq;
	}

	public MedicationForm getForm() {
		return form;
	}

	public void setForm(MedicationForm form) {
		this.form = form;
	}

	public List<Medication> getAlternatives() {
		return alternatives;
	}

	public void setAlternatives(List<Medication> alternatives) {
		this.alternatives = alternatives;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<MedicationDTO> getDTOAlternatives() {
		List<MedicationDTO> alternatives = new ArrayList<MedicationDTO>();
		for (Medication med : this.alternatives) {
			alternatives.add(new MedicationDTO(med));
		}
		return alternatives;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}
	
}
