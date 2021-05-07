package mrsisa12.pharmacy.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.repository.EmploymentRepository;

@Service
public class EmploymentService {

	@Autowired
	private EmploymentRepository employmentRepository;
	

	public Employment findOne(Long id) {
		return employmentRepository.findById(id).orElse(null);
	}

	public List<Employment> findAll() {
		return employmentRepository.findAll();
	}

	public Page<Employment> findAll(Pageable page) {
		return employmentRepository.findAll(page);
	}

	public Employment save(Employment employment) {
		return employmentRepository.save(employment);
	}

	@Transactional(readOnly = false)
	public void deleteEmployment(Employment employment) {
		employmentRepository.delete(employment);
	}

	public boolean checkOtherWorkTimes(TimePeriod workTime, Employee employee) {

		List<Employment> dermaEmployments = employmentRepository.findAllByEmployee(employee);

		LocalTime workTimeSTime = workTime.getStartTime();
		LocalTime workTimeETime = workTime.getEndTime();

		for (Employment employment : dermaEmployments) {
			LocalTime empWorkTimeSTime = employment.getWorkTime().getStartTime();
			LocalTime empWorkTimeETime = employment.getWorkTime().getEndTime();
			if (!(empWorkTimeSTime.isAfter(workTimeSTime) && empWorkTimeSTime.isAfter(workTimeETime))
					&& !(empWorkTimeETime.isBefore(workTimeSTime) && empWorkTimeETime.isBefore(workTimeETime)))
				return false; // preklapanje sa radnim vremenom u drugoj apoteci
		}

		return true;
	}

	public List<Employment> findAllPharmacistEmploymentsByTime(LocalTime startTime) {
		List<Employment> pharmEmployments = employmentRepository.findAllPharmacistEmployments();
		List<Employment> matches = new ArrayList<Employment>();
		for (Employment e : pharmEmployments) {
			LocalTime workStartTime = e.getWorkTime().getStartTime();
			LocalTime workEndTime = e.getWorkTime().getEndTime();
			if (startTime.isAfter(workStartTime) && startTime.isBefore(workEndTime.minusHours(1).plusMinutes(1))) {//todo dodaj pravo trajanje pregleda a ne 1h
				System.out.println(e.getEmployee().getFirstName());
			
				matches.add(e);
			}
		}
		

		return matches;
	}

	public List<Employment> findAllPharmacistEmployments() {
		return employmentRepository.findAllPharmacistEmployments();
	}
}
