package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.dto.ItemPriceDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.repository.PharmacyRepository;

@Service
public class PharmacyService {

	@Autowired
	private PharmacyRepository pharmacyRepository;

	public Pharmacy findOne(Long id) {
		return pharmacyRepository.findById(id).orElseGet(null);
	}

	public List<Pharmacy> findAll() {
		return pharmacyRepository.findAll();
	}

	public Page<Pharmacy> findAll(Pageable page) {
		return pharmacyRepository.findAll(page);
	}

	public Pharmacy save(Pharmacy pharmacy) {
		return pharmacyRepository.save(pharmacy);
	}

	public void remove(Long id) {
		pharmacyRepository.deleteById(id);
	}

	public List<Pharmacy> findAllByName(String name) {
		return pharmacyRepository.findAllByName(name);
	}

	public List<Pharmacy> findByNameAllIgnoringCase(String name) {
		return pharmacyRepository.findByNameAllIgnoringCase(name);
	}

	public Pharmacy findOneWithStorageItems(Long pharmacyId) {
		return pharmacyRepository.findOneWithStorageItems(pharmacyId);
	}

	public Pharmacy findOneByStorageItem(Long storageItemId) {
		return pharmacyRepository.findOneByStorageItem(storageItemId);
	}

	public List<Pharmacy> findByQuery(String query) {
		return pharmacyRepository.findByQuery(query);
	}

	public Pharmacy findOneWithEmployments(Long id) {
		return pharmacyRepository.findOneWithEmployments(id);
	}
	
	public Pharmacy findOneWithPharmacyAdmins(Long id) {
		return pharmacyRepository.findOneWithPharmacyAdmins(id);
	}

}
