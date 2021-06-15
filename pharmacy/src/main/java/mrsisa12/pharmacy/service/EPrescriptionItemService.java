package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.EPrescriptionItem;
import mrsisa12.pharmacy.repository.EPrescriptionItemRepository;

@Service
public class EPrescriptionItemService {

	@Autowired
	private EPrescriptionItemRepository ePrescriptionItemRepository;

	public EPrescriptionItem save(EPrescriptionItem ePrescriptionItem) 
	{
		return ePrescriptionItemRepository.save(ePrescriptionItem);
	}
	
}
