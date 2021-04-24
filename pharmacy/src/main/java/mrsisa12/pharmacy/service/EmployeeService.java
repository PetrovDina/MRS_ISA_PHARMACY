package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findOne(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

}
