package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.SupplierStorageItem;
import mrsisa12.pharmacy.repository.SupplierStorageItemRepository;

@Service
public class SupplierStorageItemService {
	
	@Autowired
	private SupplierStorageItemRepository supplierStorageItemRepository;
	
	public SupplierStorageItem findOne(Long id) 
	{
		return supplierStorageItemRepository.findById(id).orElseGet(null);
	}
	
	public Page<SupplierStorageItem> findAll(Pageable page) 
	{
		return supplierStorageItemRepository.findAll(page);
	}
	
	public SupplierStorageItem save(SupplierStorageItem supplierStorageItem) 
	{
		return supplierStorageItemRepository.save(supplierStorageItem);
	}
	
	@Transactional(readOnly = false)
	public void deleteSupplierStorageItem(SupplierStorageItem supplierStorageItem) 
	{
		supplierStorageItemRepository.delete(supplierStorageItem);
	}
	
	public SupplierStorageItem findOneWithMedication(Long supplierStorageItemId) 
	{
		return supplierStorageItemRepository.findOneWithMedication(supplierStorageItemId);
	}

}
