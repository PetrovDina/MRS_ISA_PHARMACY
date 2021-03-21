package mrsisa12.pharmacy.model;

public class Medication {

	private Integer id;
	private String medicationName;
	
	public Medication() {
		
	}
	
	public Medication(Integer id, String medicationName) {
		super();
		this.id = id;
		this.medicationName = medicationName;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMedicationName() {
		return medicationName;
	}
	public void setMedicationName(String medicationName) {
		this.medicationName = medicationName;
	}

	public void copyValues(Medication medication) {
		this.medicationName = medication.getMedicationName();
		
	}
	
}
