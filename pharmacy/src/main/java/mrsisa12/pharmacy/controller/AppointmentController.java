package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.AppointmentDTO;
import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyService pharmacyService;
		
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	

	@GetMapping(value = "/all")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {

		List<Appointment> appointments = appointmentService.findAll();

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/scheduledDermByPatient")
	public ResponseEntity<List<AppointmentDTO>> getAllScheduledDermByPatient(@RequestParam String patientUsername) {

		List<Appointment> appointments = appointmentService.getAllScheduledDermByPatient(patientUsername);

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/scheduledPharmByPatient")
	public ResponseEntity<List<AppointmentDTO>> getAllScheduledPharmByPatient(@RequestParam String patientUsername) {

		List<Appointment> appointments = appointmentService.getAllScheduledPharmByPatient(patientUsername);

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/dermHistoryByPatient")
	public ResponseEntity<List<AppointmentDTO>> getAllDermHistoryByPatient(@RequestParam String patientUsername) {

		List<Appointment> appointments = appointmentService.getAllDermHistoryByPatient(patientUsername);

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/pharmHistoryByPatient")
	public ResponseEntity<List<AppointmentDTO>> getAllPharmHistoryByPatient(@RequestParam String patientUsername) {

		List<Appointment> appointments = appointmentService.getAllPharmHistoryByPatient(patientUsername);

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/cancelDerm")
	public ResponseEntity<AppointmentDTO> cancelDermAppointment(@RequestParam Long appointmentId, @RequestParam String patientUsername) {
		Appointment appointment = appointmentService.findOne(appointmentId);
		if(appointment != null) {
			
			//setting appointment status to cancelled
			appointment.setStatus(AppointmentStatus.CANCELLED);
			
			Patient patient = patientService.findByUsername(patientUsername);
			Integer pointsToLoose = loyaltyProgramService.appointmentPoints();
			patientService.addPointsAndUpdateCategory(patient, (-pointsToLoose));
			
    		appointmentService.save(appointment);
    		
    		//creating new free appointment
    		Appointment freeApp = new Appointment(appointment);
    		freeApp.setStatus(AppointmentStatus.AVAILABLE);
    		freeApp.setPatient(null);
    		appointmentService.save(freeApp);
    		
            return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/cancelPharm")
	public ResponseEntity<AppointmentDTO> cancelPharmAppointment(@RequestParam Long appointmentId, @RequestParam String patientUsername) {
		Appointment appointment = appointmentService.findOne(appointmentId);
		if(appointment != null) {
			
			//setting appointment status to cancelled
			appointment.setStatus(AppointmentStatus.CANCELLED);
			
			Patient patient = patientService.findByUsername(patientUsername);
			Integer pointsToLoose = loyaltyProgramService.appointmentPoints();
			patientService.addPointsAndUpdateCategory(patient, (-pointsToLoose));
			
    		appointmentService.save(appointment);
    		
            return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/allDermatologistAvailable")
	public ResponseEntity<List<AppointmentDTO>> getAllAvailableDermatologistAppointments() {

		List<Appointment> appointments = appointmentService.findAllAvailableDermatologistAppointments();


		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/checkIfCanBookDerm")
	public ResponseEntity<Boolean> checkIfCanBookDerm(@RequestParam Long appointmentId, @RequestParam String patientUsername) {

		List<Appointment> appointments = appointmentService.getAllDermHistoryByPatient(patientUsername);
		Appointment appointment = appointmentService.findOne(appointmentId);
		for (Appointment a : appointments) {
			if (a.getStatus() == AppointmentStatus.CANCELLED && a.getTimePeriod().getStartDate().equals(appointment.getTimePeriod().getStartDate())
					&& a.getTimePeriod().getStartTime().equals(appointment.getTimePeriod().getStartTime()) 
					&& appointment.getEmployee().getId() == a.getEmployee().getId()
					&& appointment.getPharmacy().getId() == a.getPharmacy().getId()){
				return new ResponseEntity<>(false, HttpStatus.OK);
			}
		}


		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/checkIfCanBookPharm")
	public ResponseEntity<Boolean> checkIfCanBookPharm( @RequestParam String patientUsername, @RequestParam String startDate,
			@RequestParam String startTime, @RequestParam Long employeeId, @RequestParam Long pharmacyId ) {

		List<Appointment> appointments = appointmentService.getAllPharmHistoryByPatient(patientUsername);
		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalTime startTime2 = LocalTime.parse(startTime);
		
		for (Appointment a : appointments) {
			if (a.getStatus() == AppointmentStatus.CANCELLED && a.getTimePeriod().getStartDate().equals(startDate2)
					&& a.getTimePeriod().getStartTime().equals(startTime2) 
					&& employeeId == a.getEmployee().getId()
					&& pharmacyId == a.getPharmacy().getId()){
				return new ResponseEntity<>(false, HttpStatus.OK);
			}
		}


		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	

	@GetMapping
	public ResponseEntity<List<AppointmentDTO>> getAppointmentsPage(Pageable page) {

		// page object holds data about pagination and sorting
		// the object is created based on the url parameters "page", "size" and "sort"
		Page<Appointment> appointments = appointmentService.findAll(page);

		// convert appointments to DTOs
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment m : appointments) {
			appointmentsDTO.add(new AppointmentDTO(m));
		}

		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long id) {

		Appointment appointment = appointmentService.findOne(id);

		// appointment must exist
		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping(value = "/savePharmacistAppointment", consumes = "application/json")
	public ResponseEntity<String> savePharmacistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		String message;
		try {
			message = appointmentService.bookPharmacistAppointment(appointmentDTO);
		}
		catch (Exception e) {
			message = "Reservation failed, try again later.";
		}
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<AppointmentDTO> saveDermatologistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		Appointment appointment;
		try {
			appointment = appointmentService.createDermatologistAppointment(appointmentDTO);
		}
		catch (Exception e) {
			return new ResponseEntity<AppointmentDTO>(new AppointmentDTO(), HttpStatus.NOT_ACCEPTABLE);
		}
		return new ResponseEntity<AppointmentDTO>(new AppointmentDTO(appointment), HttpStatus.CREATED);
		
	}
	
	//derm
	@GetMapping(value = "/book")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> reserveAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId) {
		String message;
		try {
			message = appointmentService.reserveAppointment(patientUsername, appointmentId);
		} catch (ObjectOptimisticLockingFailureException e) {
			message = "Reservation failed, try again later.";
		}

		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<AppointmentDTO> updateAppointment(@RequestBody AppointmentDTO appointmentDTO) {

		Appointment appointment = appointmentService.findOne(appointmentDTO.getId());

		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// PRETPOTAVLJAM DA CE SE MOCI UREDJIVATI SAMO VRIJEME

//		// postavljamo dermatologa ili farmaceuta na termin
//		Employee emp = employeeService.findOne(appointment.getEmployee().getId());
//		appointment.setEmployee(emp);
//		// postavljamo status da je dostupan jer se kreira
//		appointment.setStatus(appointmentDTO.getStatus());
//		// postavljamo pacijenta na novi slobodni termin
//		Patient patient = patientService.findOne(appointmentDTO.getPatientDTO().getId());
//		appointment.setPatient(patient);
		// Trebace vjerovatno da se nesto uradi sa datumom.
		// appointment.setTimePeriod(appointmentDTO.getTimePeriod());

		appointment = appointmentService.save(appointment);
		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {

		Appointment appointment = appointmentService.findOne(id);

		if (appointment != null) {
			appointmentService.deleteAppointment(appointment);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/upcomingPatientsForEmployee")
	public ResponseEntity<List<PatientDTO>> getUpcomingPatientsForEmployee(@RequestParam String username) {		
		List<PatientDTO> unique = new ArrayList<>();
		unique = appointmentService.getUpcomingPatientsForEmployee(username);
        
		return new ResponseEntity<>(unique, HttpStatus.OK);
	}
		
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/upcomingAppointmentsForPatient")
	public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointmentsForEmployee(@RequestParam("patientUsername") String patientUsername, @RequestParam String employeeUsername) {
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		appointmentsDTO = appointmentService.getUpcomingAppointmentsForEmployee(patientUsername, employeeUsername);
				
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
			
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/allAppointmentsForEmployee")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForEmployee( @RequestParam String employeeUsername, @RequestParam String minDate, @RequestParam String maxDate) {	
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		appointmentsDTO = appointmentService.getAllAppointmentsForEmployee(employeeUsername,minDate, maxDate);
				
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/finishAppointment")
	public ResponseEntity<Void> finishAppointment(  @RequestParam String appointmentId, @RequestParam String report) {	
		Appointment appointment = appointmentService.findOne(Long.parseLong(appointmentId));
		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		appointment.setInProgress(false);
		appointment.setReport(report);
		appointment.setStatus(AppointmentStatus.CONCLUDED);
		appointment = appointmentService.save(appointment);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/patientDidntShowUp")
	public ResponseEntity<String> patientDidntShowUp(@RequestParam Long appointmentId) {	
		
		String message;
		try {
			message = appointmentService.patientDidntShowUp(appointmentId);
		} catch (ObjectOptimisticLockingFailureException e) {
			message = "Failed to change appointment status, try again later.";
		}

		return new ResponseEntity<>(message, HttpStatus.CREATED);
		
	}
		
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACY_ADMIN')")
	@GetMapping(value = "/getAvailableDermAppointments")
	public ResponseEntity<List<AppointmentDTO>> getAvailableDermAppointments(@RequestParam String employeeUsername,@RequestParam String pharmacyId){				
		List<AppointmentDTO> avail = new ArrayList<AppointmentDTO>();
		avail = appointmentService.getAvailableDermAppointments(employeeUsername, pharmacyId);
		return new ResponseEntity<>(avail, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@PostMapping(value = "/createNewAppointmentByEmployee", consumes = "application/json")
	public ResponseEntity<String> createNewAppointmentByEmployee(@RequestBody AppointmentDTO appointmentDTO){
		
		String message;
		try {
			message = appointmentService.createNewAppointmentByEmployee(appointmentDTO);
		}
		catch (Exception e) {
			message = "Reservation failed, try again later.";
		}
		
		return new ResponseEntity<>(message, HttpStatus.CREATED);		
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/bookAvailableAppointment")
	public ResponseEntity<String> bookAvailableAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId){
		
		String message;
		try {
			message = appointmentService.bookAvailableAppointment(patientUsername, appointmentId);
		} catch (ObjectOptimisticLockingFailureException e) {
			message = "Reservation failed, try again later.";
		}

		return new ResponseEntity<>(message, HttpStatus.CREATED);		
	}
	
	@GetMapping(value = "/reportAppointmentYear")
	public ResponseEntity<ReportDTO> reportAppointmentYear(@RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = appointmentService.getAllConcludedApointmentsByYear(year, pharmacyService.findOne(pharmacyId));
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportAppointmentQuarter")
	public ResponseEntity<ReportDTO> reportAppointmentQuarter(@RequestParam String quarter, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = appointmentService.getAllConcludedApointmentsByQuarter(quarter, year, pharmacyService.findOne(pharmacyId), null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportAppointmentMonth")
	public ResponseEntity<ReportDTO> reportAppointmentMonth(@RequestParam String period, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = appointmentService.getAllAppointmentsConcludedByMonthInYear(period, year, pharmacyService.findOne(pharmacyId), null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/setAppointmentInProgress")
	public ResponseEntity<String> setAppointmentInProgress( @RequestParam Long appointmentId) {
		String message;
		try {
			message = appointmentService.setAppointmentInProgress(appointmentId);
		} catch (ObjectOptimisticLockingFailureException e) {
			message = "Failed to start appointment, try again later.";
		}

		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/allConcludedAppointmentsForEmployee")
	public ResponseEntity<List<AppointmentDTO>> allConcludedAppointmentsForEmployee( @RequestParam String employeeUsername) {	
		List<Appointment> appointments = appointmentService.getAllConcludedAppointmentsForEmployee(employeeUsername);
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			appointmentsDTO.add(new AppointmentDTO(appointment));
		}
		
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
}
