package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.repository.PharmacyStorageItemRepository;

@Service
public class PharmacyStorageItemService {

	@Autowired
	private PharmacyStorageItemRepository pharmacyStorageItemRepository;
	
	public PharmacyStorageItem findOne(Long id) {
		return pharmacyStorageItemRepository.findById(id).orElseGet(null);
	}
	
	public List<PharmacyStorageItem> findAll() {
		return pharmacyStorageItemRepository.findAll();
	}
	
	public Page<PharmacyStorageItem> findAll(Pageable page) {
		return pharmacyStorageItemRepository.findAll(page);
	}
	
	public PharmacyStorageItem save(PharmacyStorageItem pharmacyStorageItem) {
		return pharmacyStorageItemRepository.save(pharmacyStorageItem);
	}

	public void remove(Long id) {
		pharmacyStorageItemRepository.deleteById(id);
	}
	
	public PharmacyStorageItem findOneWithItemPrices(Long pharmacyStorageItemId) {
		return pharmacyStorageItemRepository.findOneWithItemPrices(pharmacyStorageItemId);
	}
	
	public List<PharmacyStorageItem> findAllWithCurrentPriceByMedication(Long medicationId) {
		return pharmacyStorageItemRepository.findAllWithCurrentPriceByMedication(medicationId);
	}
	
	
}
