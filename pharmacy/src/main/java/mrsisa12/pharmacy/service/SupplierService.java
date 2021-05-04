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
	
}
