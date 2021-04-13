package mrsisa12.pharmacy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "form", nullable = false)
	private MedicationForm form;

	public Medication() {

	}

	public Medication(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form) {
		super();
		this.name = name;
		this.manufacturer = manufacturer;
		this.prescriptionReq = prescriptionReq;
		this.form = form;
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

}
