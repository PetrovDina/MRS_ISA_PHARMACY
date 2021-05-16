package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.dto.pharmacy.PlainPharmacyDTO;
import mrsisa12.pharmacy.model.ComplaintPharmacy;

public class ComplaintUserPharmacyDTO extends ComplaintUserDTO {
	
	private PlainPharmacyDTO pharmacy;
	
	public ComplaintUserPharmacyDTO() { }

	public ComplaintUserPharmacyDTO(Long id, String content, PlainPharmacyDTO pharmacy) {
		super(id, content);
		this.pharmacy = pharmacy;
	}

	public ComplaintUserPharmacyDTO(ComplaintPharmacy c) {
		this(c.getId(), c.getContent(), new PlainPharmacyDTO(c.getPharmacy()));
	}

	public PlainPharmacyDTO getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(PlainPharmacyDTO pharmacy) {
		this.pharmacy = pharmacy;
	}

}
