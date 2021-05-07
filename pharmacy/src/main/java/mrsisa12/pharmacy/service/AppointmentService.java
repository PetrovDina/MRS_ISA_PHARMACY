package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Appointment;
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
}