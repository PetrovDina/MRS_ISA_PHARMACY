package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.model.EmployeeRating;
import mrsisa12.pharmacy.repository.EmployeeRatingRepository;

@Service
public class EmployeeRatingService {

	@Autowired
	private EmployeeRatingRepository employeeRatingRepository;

	public void save(EmployeeRating er)
	{
		employeeRatingRepository.save(er);
	}
	
	public EmployeeRating findOne(Long id) {
		return employeeRatingRepository.findById(id).orElseGet(null);
	}

	public List<EmployeeRating> findAll() {
		return employeeRatingRepository.findAll();
	}

	public EmployeeRating findOneByPatientAndEmployee(String patientUsername, Long employeeId) {
		return employeeRatingRepository.findOneByPatientAndEmployee(patientUsername, employeeId);
	}

	public List<EmployeeRating> findAllByEmployee(Long employeeId) {
		return employeeRatingRepository.findAllByEmployee(employeeId);
	}
}
