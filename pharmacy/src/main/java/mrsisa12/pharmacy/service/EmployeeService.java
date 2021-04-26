package mrsisa12.pharmacy.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.TimePeriod;
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

	public Employee findOneWithAllAppointments(Long id) {
		return employeeRepository.findOneWithAllAppointments(id);
	}

	public boolean checkAppointmentTime(TimePeriod timePeriod, Long id) {
		Employee employee = employeeRepository.findOneWithAllAppointments(id);
		// prvo provjera da li je u okvriu radnog vremena -- Samo LocalTime provjeravamo
		LocalTime eWorkTSTime = employee.getWorkTime().getStartTime();
		LocalTime eWorkTETime = employee.getWorkTime().getEndTime();
		if (!eWorkTSTime.isBefore(timePeriod.getStartTime()) || !eWorkTETime.isAfter(timePeriod.getEndTime())) {
			// ne upada u opseg radnog vremena
			return false;
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
				return false; // preklapanje
		}
		// ako je u okviru radnog vremena i ne preklapa se ni sa jednim terminom koji
		// postoji dobro je!
		return true;
	}

}
