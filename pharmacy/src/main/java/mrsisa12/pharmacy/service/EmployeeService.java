package mrsisa12.pharmacy.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.repository.EmployeeRepository;
import mrsisa12.pharmacy.repository.EmploymentRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmploymentRepository employmentRepository;

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public void save(Employee employee) {
		employeeRepository.save(employee);
	}

	public Employee findOne(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	public Employee findOneWithAllAppointments(Long id) {
		return employeeRepository.findOneWithAllAppointments(id);
	}

	public boolean checkAppointmentTime(TimePeriod timePeriod, Long id) {

		Employee employee = employeeRepository.findOneWithAllAppointments(id);

		List<Employment> employments = employmentRepository.findAllByEmployee(employee);

		LocalTime timePeriodSTime = timePeriod.getStartTime();
		LocalTime timePeriodETime = timePeriod.getEndTime();

		for (Employment employment : employments) {
			if (!employment.getWorkTime().getStartTime().isBefore(timePeriodSTime)
					|| !employment.getWorkTime().getEndTime().isAfter(timePeriodETime))
				return false; // ne upada u radno vrijeme
		}

		// Ovdje se mora provjeravati i datum i vrijeme
		for (Appointment appo : employee.getAppointments()) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate()
					.atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(timePeriod.getEndDate().atTime(timePeriod.getEndTime()))
					&& eWorkTSDateTime.isAfter(timePeriod.getStartDate().atTime(timePeriod.getStartTime())))
					&& !(eWorkTEDateTime.isBefore(timePeriod.getEndDate().atTime(timePeriod.getEndTime()))
							&& eWorkTEDateTime.isBefore(timePeriod.getStartDate().atTime(timePeriod.getStartTime()))))
				return false; // preklapanje sa postojecim terminom
		}
		// ako je u okviru radnog vremena i ne preklapa se ni sa jednim terminom koji
		// postoji dobro je!
		return true;
	}
	
	public Employee findOneByUsername(String username) {
		return employeeRepository.findByUsername(username);
	}

}
