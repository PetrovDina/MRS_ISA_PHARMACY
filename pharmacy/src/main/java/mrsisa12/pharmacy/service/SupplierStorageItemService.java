package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Supplier;
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
	
	public void updateSupplierStorageItemQuantity(Medication medication, Supplier supplier, int quantity) 
	{
		SupplierStorageItem supplierStorageItem = supplierStorageItemRepository.findOneByMedication(medication, supplier);
		// umanjujemo kolicinu za porucenu kolicinu
		supplierStorageItem.setQuantity(supplierStorageItem.getQuantity() - quantity);
		supplierStorageItem.setReservedQuantity(supplierStorageItem.getReservedQuantity() - quantity);
		supplierStorageItemRepository.save(supplierStorageItem);
	}
	
	public SupplierStorageItem findOneWithMedication(Long supplierStorageItemId) 
	{
		return supplierStorageItemRepository.findOneWithMedication(supplierStorageItemId);
	}
	
	public void updateSupplierStorageItemReservedQuantity(Medication medication, Supplier supplier, int quantityToReserve) 
    {
        SupplierStorageItem supplierStorageItem = supplierStorageItemRepository.findOneByMedication(medication, supplier);
        System.out.println(supplierStorageItem);
        supplierStorageItem.setReservedQuantity(supplierStorageItem.getReservedQuantity() + quantityToReserve);
        supplierStorageItemRepository.save(supplierStorageItem);
    }

}
