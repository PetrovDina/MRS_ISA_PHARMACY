package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.ComplaintEmployee;
import mrsisa12.pharmacy.repository.ComplaintEmployeeRepository;

@Service
public class ComplaintEmployeeService {
	
	@Autowired
	private ComplaintEmployeeRepository complaintEmployeeRepository;
	
	public List<ComplaintEmployee> findAll() 
	{
		return complaintEmployeeRepository.findAll();
	}
	
	public void save(ComplaintEmployee complaint)
	{
		complaintEmployeeRepository.save(complaint);
	}

}
