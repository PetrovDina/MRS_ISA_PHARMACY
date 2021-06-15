package mrsisa12.pharmacy.dto;

import java.util.List;

public class QrCodeDTO {
	
	private List<MedicationQrDTO> medications;
	private String code;
	
	public QrCodeDTO() { }
	
	public QrCodeDTO(List<MedicationQrDTO> medications, String code) {
		super();
		this.medications = medications;
		this.code = code;
	}

	public List<MedicationQrDTO> getMedications() {
		return medications;
	}

	public void setMedications(List<MedicationQrDTO> medications) {
		this.medications = medications;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
