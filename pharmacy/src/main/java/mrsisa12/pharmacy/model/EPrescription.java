package mrsisa12.pharmacy.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class EPrescription {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(name = "prescribedDate", nullable = false)
	private Date prescribedDate;
	
	@Column(name = "patientFirstName", nullable = false)
	private String patientFirstName;
	
	@Column(name = "patientLastName", nullable = false)
	private String patientLastName;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private Patient patient;
	
	@Column(name = "code", nullable = false, length =  10)
	private String code;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ePrescription")
	private List<EPrescriptionItem> prescriptionItems;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pharmacy_id", referencedColumnName = "id", nullable = false)
	private Pharmacy pharmacy;
	
	public EPrescription() { }

	public EPrescription(Date prescribedDate, String patientFirstName, String patientLastName, String code,
			List<EPrescriptionItem> prescriptionItems, Pharmacy pharmacy, Double price, Patient patient) {
		super();
		this.prescribedDate = prescribedDate;
		this.patientFirstName = patientFirstName;
		this.patientLastName = patientLastName;
		this.code = code;
		this.prescriptionItems = prescriptionItems;
		this.pharmacy = pharmacy;
		this.price = price;
		this.patient = patient;
	}
	
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
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

	public void setPatientLastName(String patientLastName) {
		this.patientLastName = patientLastName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<EPrescriptionItem> getPrescriptionItems() {
		return prescriptionItems;
	}

	public void setPrescriptionItems(List<EPrescriptionItem> prescriptionItems) {
		this.prescriptionItems = prescriptionItems;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
