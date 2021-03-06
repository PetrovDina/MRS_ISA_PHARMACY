package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.repository.SupplierRepository;

@Service
public class SupplierService {

	@Autowired
	private SupplierRepository supplierRepository;
	
	public List<Supplier> findAll() 
	{
		return supplierRepository.findAll();
	}
	
	public void save(Supplier supplier)
	{
		supplierRepository.save(supplier);
	}
	
	public Supplier findOneWithStorageItems(Long supplierId) 
	{
		return supplierRepository.findOneWithStorageItems(supplierId);
	}
	
	public Supplier findOneWithStorageItems(String supplierUsername) 
	{
		return supplierRepository.findOneWithStorageItems(supplierUsername);
	}
	
	public Supplier findOne(String username)
	{
		return supplierRepository.findOne(username);
	}
	
}
