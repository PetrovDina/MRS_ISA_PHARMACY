package mrsisa12.pharmacy.dto;

import java.util.ArrayList;

import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.TherapyItem;

public class TherapyWithItemsDTO extends TherapyDTO {

	private ArrayList<TherapyItemDTO> prescriptionItems = new ArrayList<TherapyItemDTO>();

	public ArrayList<TherapyItemDTO> getPrescriptionItems() {
		return prescriptionItems;
	}

	public void setPrescriptionItems(ArrayList<TherapyItemDTO> prescriptionItems) {
		this.prescriptionItems = prescriptionItems;
	}

	public TherapyWithItemsDTO(Therapy ep) {
		super(ep);
		for (TherapyItem e : ep.getPrescriptionItems()) {
			this.prescriptionItems.add(new TherapyItemDTO(e));
			
		}
	}


	
}
