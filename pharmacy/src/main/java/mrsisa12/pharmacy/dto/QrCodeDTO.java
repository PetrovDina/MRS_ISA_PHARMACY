package mrsisa12.pharmacy.dto;

import java.util.List;

public class QrCodeDTO {
	
	private List<MedicationQrDTO> medications;
	
	public QrCodeDTO() { }
	
	public QrCodeDTO(List<MedicationQrDTO> medications) {
		super();
		this.medications = medications;
	}

	public List<MedicationQrDTO> getMedications() {
		return medications;
	}

	public void setMedications(List<MedicationQrDTO> medications) {
		this.medications = medications;
	}
	
}
