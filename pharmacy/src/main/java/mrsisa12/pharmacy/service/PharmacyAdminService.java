package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.repository.PharmacyAdminRepository;

@Service
public class PharmacyAdminService 
{
	@Autowired
	private PharmacyAdminRepository pharmacyAdminRepository;
	
	public List<PharmacyAdmin> findAll() 
	{
		return pharmacyAdminRepository.findAll();
	}
	
	public void save(PharmacyAdmin pharmacyAdmin)
	{
		pharmacyAdminRepository.save(pharmacyAdmin);
	}

}
