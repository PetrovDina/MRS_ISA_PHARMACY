package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.enums.MedicationForm;

public class MedicationDTO {

	private Long id;
	private String name;
	private String manufacturer;
	private boolean prescriptionReq;
	private MedicationForm form;
	private String nameAndForm;
	private double rating;
	
	public MedicationDTO() {
		
	}
	
	public MedicationDTO(Medication medication) {
		this(medication.getId(), medication.getName(), medication.getManufacturer(), medication.isPrescriptionReq(), medication.getForm());
		this.nameAndForm = medication.getName() + " (" + medication.getForm().toString() + ")";
		this.rating = medication.getRating();
	}

	public MedicationDTO(Long id, String name, String manufacturer, boolean prescriptionReq, MedicationForm form) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.prescriptionReq = prescriptionReq;
		this.form = form;
		this.nameAndForm = name + " (" + form.toString() + ")";
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
	
	public String getNameAndForm() {
		return nameAndForm;
	}

	public void setNameAndForm(String nameAndForm) {
		this.nameAndForm = nameAndForm;
	}

	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "MedicationDTO [id=" + id + ", name=" + name + ", manufacturer=" + manufacturer + ", prescriptionReq="
				+ prescriptionReq + ", form=" + form + "]";
	}
}
