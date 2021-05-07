package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mrsisa12.pharmacy.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

	public Page<Appointment> findAll(Pageable pageable);

	/*
	 * Pronalazi sve objekte tipa Medication i vraca onoliko objekata koliko je
	 * specificirano kroz Pageable objekat. Npr. ako se prosledi objekat: new
	 * PageRequest(0, 10) vratice se nulta stranica sa prvih 10 objekata tipa
	 * Medication. Vise informacija na:
	 * http://docs.spring.io/autorepo/docs/spring-data-commons/1.10.0.RC1/api/org/
	 * springframework/data/domain/PageRequest.html
	 */

	public List<Appointment> findAllByEmployeeId(Long id);

	public List<Appointment> findAllByPatientId(Long id);

	@Query("select app from Appointment app where app.status = 'AVAILABLE' and app.type = 'DERMATOLOGIST_EXAMINATION'")
	public List<Appointment> findAllAvailableDermatologistAppointments();

	
	@Query("select app from Appointment app where app.status = 'RESERVED' and app.type = 'DERMATOLOGIST_EXAMINATION' and app.patient.username = :username")
	public List<Appointment> getAllScheduledDermByPatient(@Param("username") String patientUsername);

	
	@Query("select app from Appointment app where app.status = 'RESERVED' and app.type = 'PHARMACIST_CONSULTATION' and app.patient.username = :username")
	public List<Appointment> getAllScheduledPharmByPatient(@Param("username") String patientUsername);
}
