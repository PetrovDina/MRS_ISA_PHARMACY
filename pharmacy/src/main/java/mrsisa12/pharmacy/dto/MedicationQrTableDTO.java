package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Medication;

public class MedicationQrTableDTO {

	private Long id;
	private String name;
	private String manufacturer;
	private double quantity;
	
	public MedicationQrTableDTO() { }
	
	public MedicationQrTableDTO(Long id, String name, String manufacturer, double quantity) {
		super();
		this.id = id;
		this.name = name;
		this.manufacturer = manufacturer;
		this.quantity = quantity;
	}
	
	public MedicationQrTableDTO(Medication med, double quantity) {
		this(med.getId(), med.getName(), med.getManufacturer(), quantity);
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
	
	public double getQuantity() {
		return quantity;
	}
	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}


}
