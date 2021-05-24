package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public Appointment findOne(Long id) {
		return appointmentRepository.findById(id).orElseGet(null);
	}

	public List<Appointment> findAll() {
		return appointmentRepository.findAll();
	}

	public Page<Appointment> findAll(Pageable page) {
		return appointmentRepository.findAll(page);
	}

	public Appointment save(Appointment appointment) {
		return appointmentRepository.save(appointment);
	}

	public void remove(Long id) {
		appointmentRepository.deleteById(id);
	}

	public List<Appointment> findAllByEmployeetId(Long id) {
		return appointmentRepository.findAllByEmployeeId(id);
	}

	public List<Appointment> findAllByPatientId(Long id) {
		return appointmentRepository.findAllByPatientId(id);
	}

	@Transactional(readOnly = false)
	public void deleteAppointment(Appointment appointment) {
		appointmentRepository.delete(appointment);
	}

	public List<Appointment> findAllAvailableDermatologistAppointments() {
		return appointmentRepository.findAllAvailableDermatologistAppointments();
	}

	public List<Appointment> getAllScheduledDermByPatient(String patientUsername) {
		return appointmentRepository.getAllScheduledDermByPatient(patientUsername);
	}

	public List<Appointment> getAllScheduledPharmByPatient(String patientUsername) {
		return appointmentRepository.getAllScheduledPharmByPatient(patientUsername);
	}

	public List<Appointment> getAllDermHistoryByPatient(String patientUsername) {
		return appointmentRepository.getAllDermHistoryByPatient(patientUsername);
	}

	public List<Appointment> getAllPharmHistoryByPatient(String patientUsername) {
		return appointmentRepository.getAllPharmHistoryByPatient(patientUsername);
	}

	public Appointment findOneWithPatient(Long appointmentId) {
		return appointmentRepository.findOneWithPatient(appointmentId);
	}
	
	public List<Appointment> findAllByPatientUsername(String username) {
		return appointmentRepository.findAllByPatientUsername(username);
	}
	
	public HashMap<String, Integer> getAllConcludedApointmentsByYear(String year, Pharmacy pharmacy){
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		List<Appointment> appointmentsFromPharmacy = appointmentRepository.findAllFromPharmacy(pharmacy);
		String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
		for (String quarter : quarters) {
			HashMap<String, Integer> dataFromQuarter = getAllConcludedApointmentsByQuarter(quarter, year, pharmacy, appointmentsFromPharmacy);
			for(Map.Entry<String, Integer> entry : dataFromQuarter.entrySet()) {
				data.put(entry.getKey(), entry.getValue());
			}
		}
		return data;
	}
	
	public HashMap<String, Integer> getAllConcludedApointmentsByQuarter(String quarter, String year, Pharmacy pharmacy, List<Appointment> appointments){
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		if(appointments == null)
			appointments = appointmentRepository.findAllFromPharmacy(pharmacy);
		if(ReportDTO.quarters.containsKey(quarter)) {
			String[] monthsInQuarter = ReportDTO.quarters.get(quarter).split(",");
			for (String month : monthsInQuarter) {
				String monthTrimmed = month.trim();
				data.put(monthTrimmed, 0);
				HashMap<String, Integer> dataFromMonth = getAllAppointmentsConcludedByMonthInYear(monthTrimmed, year, pharmacy, appointments);
				for(Map.Entry<String, Integer> entry : dataFromMonth.entrySet()) {
				    data.put(monthTrimmed, data.get(monthTrimmed) + entry.getValue());
				}
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllAppointmentsConcludedByMonthInYear(String period, String year, Pharmacy pharmacy, List<Appointment> appointments) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		LocalDate startDate, endDate;
		
		String month = (ReportDTO.ReportMonths.valueOf(period).ordinal() < 9) ? "0"+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1) : ""+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1);
		LocalDate initial = LocalDate.parse(year+"-"+ month +"-01");
		startDate = initial.withDayOfMonth(1);
		endDate = initial.withDayOfMonth(initial.lengthOfMonth());
		if(appointments == null) 
			appointments = appointmentRepository.findAllFromPharmacy(pharmacy);
		
		Calendar start = Calendar.getInstance();
		start.setTime(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		Calendar end = Calendar.getInstance();
		end.setTime(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for (LocalDate date = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) {
		    String dayInMonth = String.valueOf(date.getDayOfMonth());
		    data.put(dayInMonth, 0);
		    for (Appointment appointment : appointments) {
				if(date.isEqual(appointment.getTimePeriod().getStartDate())) {
					data.put(dayInMonth, data.get(dayInMonth) + 1);
				}
			}
		}
		return data;
	}

}