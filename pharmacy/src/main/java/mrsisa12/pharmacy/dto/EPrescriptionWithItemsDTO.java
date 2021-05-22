package mrsisa12.pharmacy.dto;

import java.util.ArrayList;

import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.PrescriptionItem;

public class EPrescriptionWithItemsDTO extends EPrescriptionDTO {

	private ArrayList<PrescriptionItemDTO> prescriptionItems = new ArrayList<PrescriptionItemDTO>();

	public ArrayList<PrescriptionItemDTO> getPrescriptionItems() {
		return prescriptionItems;
	}

	public void setPrescriptionItems(ArrayList<PrescriptionItemDTO> prescriptionItems) {
		this.prescriptionItems = prescriptionItems;
	}

	public EPrescriptionWithItemsDTO(EPrescription ep) {
		super(ep);
		for (PrescriptionItem e : ep.getPrescriptionItems()) {
			this.prescriptionItems.add(new PrescriptionItemDTO(e));
			
		}
	}


	
}
