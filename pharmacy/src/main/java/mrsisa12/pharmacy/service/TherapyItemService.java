package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.repository.TherapyItemRepository;

@Service
public class TherapyItemService {

	@Autowired
	private TherapyItemRepository prescriptionItemRepository;
	
	public TherapyItem save(TherapyItem prescriptionItem) {
		return prescriptionItemRepository.save(prescriptionItem);
	}
}
