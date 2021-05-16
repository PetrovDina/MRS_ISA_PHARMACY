package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.repository.SystemAdminRepository;

@Service
public class SystemAdminService {
	
	@Autowired
	private SystemAdminRepository systemAdminRepository;
	
	public List<SystemAdmin> findAll() 
	{
		return systemAdminRepository.findAll();
	}
	
	public void save(SystemAdmin systemAdmin)
	{
		systemAdminRepository.save(systemAdmin);
	}
	
	public SystemAdmin findOneByUsername(String username)
	{
		return systemAdminRepository.findOneByUsername(username);
	}

}
