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
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;
import mrsisa12.pharmacy.service.AbsenceService;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.EmploymentService;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private EmailService emailService;
		
	@Autowired
	private EmploymentService employmentService;
	
	@Autowired
	private AbsenceService absenceService;
	
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
	public ResponseEntity<AppointmentDTO> cancelDermAppointment(@RequestParam Long appointmentId) {
		Appointment appointment = appointmentService.findOne(appointmentId);
		if(appointment != null) {
			
			//setting appointment status to cancelled
			appointment.setStatus(AppointmentStatus.CANCELLED);
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
	public ResponseEntity<AppointmentDTO> cancelPharmAppointment(@RequestParam Long appointmentId) {
		Appointment appointment = appointmentService.findOne(appointmentId);
		if(appointment != null) {
			
			//setting appointment status to cancelled
			appointment.setStatus(AppointmentStatus.CANCELLED);
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
	
	
	//dina pravila za kreiranja termina kod FARMACEUTA!
	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping(value = "/savePharmacistAppointment", consumes = "application/json")
	public ResponseEntity<String> savePharmacistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		Appointment appointment = new Appointment();

		appointment.setTimePeriod(new TimePeriod(appointmentDTO.getTimePeriod()));
		appointment.getTimePeriod().setEndTime(appointment.getTimePeriod().getStartTime().plusHours(1)); //todo promeni da bude pravo trajanje!
		appointment.setStatus(AppointmentStatus.RESERVED); //rezervisemo ga!
		appointment.setDeleted(false);
		
		// postavljamo farmaceuta na termin
		Employee employee = employeeService.findOneWithAllAppointments(appointmentDTO.getEmployee().getId());
		appointment.setEmployee(employee);
		
		//postavljamo pacijenta na termin
		Patient patient = patientService.findByUsername(appointmentDTO.getPatient().getUsername());
		appointment.setPatient(patient);
		
		// postavljamo termin farmaceutu
		employee.addAppointment(appointment);
		
		Pharmacy pharmacy = pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
		
		appointment.setPrice(pharmacy.getAppointmentPriceCatalog().getConsultationPrice());
		
		appointment.setPharmacy(pharmacy);

		appointment.setType(AppointmentType.PHARMACIST_CONSULTATION);
		
		Double price = appointment.getPrice();
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		String message = loyaltyProgramService.generateAppointmentMessage(patient, price, pointsForPatient);
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		
		appointment = appointmentService.save(appointment);
		employeeService.save(employee); //idk da l ovo treba

		return new ResponseEntity<>(message, HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<AppointmentDTO> saveDermatologistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		// prilikom kreiranja bih trebao samo da proslijedim id zaposlenog... ID
		// pacijenta ce se naknadno ubacivati i proglasavati termin zauzetim
		Appointment appointment = new Appointment();

		// Trebace vjerovatno da se nesto uradi sa datumom.
		// appointment.setTimePeriod(appointmentDTO.getTimePeriodDTO());
//		boolean res = employeeService.checkAppointmentTime(new TimePeriod(appointmentDTO.getTimePeriod()),
//				appointmentDTO.getEmployee().getId());
//		if (!res) {
//			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
//		}
		// postavljamo vrijeme i datum
		appointment.setTimePeriod(new TimePeriod(appointmentDTO.getTimePeriod()));
		// postavljamo status da je dostupan jer se kreira
		appointment.setStatus(AppointmentStatus.AVAILABLE);
		// postavljamo da je neobrisan
		appointment.setDeleted(false);
		// postavljamo dermatologa ili farmaceuta na termin
		Employee employee = employeeService.findOneWithAllAppointments(appointmentDTO.getEmployee().getId());
		appointment.setEmployee(employee);
		// postavljamo termin dermatologu ili farmaceutu
		employee.addAppointment(appointment);
		
		Pharmacy pharmacy = pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
		
		// postavljanje cijene pregleda kod dermatologa
		appointment.setPrice(pharmacy.getAppointmentPriceCatalog().getExaminationPrice());
		
		// postavljanje apoteke
		appointment.setPharmacy(pharmacy);

		//dina: TODO dodaj AppointmentType pravi!
		appointment.setType(AppointmentType.DERMATOLOGIST_EXAMINATION);
		appointment = appointmentService.save(appointment);

		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/book")
	@PreAuthorize("hasRole('PATIENT')")
	public ResponseEntity<String> reserveAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId) {

		Patient patient = patientService.findByUsername(patientUsername);
		
		Appointment appointment = appointmentService.findOne(appointmentId);
		appointment.setPatient(patient);
		appointment.setStatus(AppointmentStatus.RESERVED);
		
		Double price = appointment.getPrice();
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		String message = loyaltyProgramService.generateAppointmentMessage(patient, price, pointsForPatient);
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		appointment = appointmentService.save(appointment);
		
		// email!
		String emailBody = "This email is confirmation that you have successfully booked your appointment with " 
		+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() + " at "
		+ appointment.getTimePeriod().getStartDate() + " " 
		+ appointment.getTimePeriod().getStartTime();
		
		EmailContent email = new EmailContent("Appointment booked", emailBody);
		email.addRecipient(appointment.getPatient().getEmail());
        emailService.sendEmail(email);

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
		Employee emp = employeeService.findOneByUsername(username);
		List<Appointment> appointments = appointmentService.findAll();
		
		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			if(appointment.getEmployee().getId().equals(emp.getId()) && appointment.getStatus() == AppointmentStatus.RESERVED) {
				patientsDTO.add(new PatientDTO(appointment.getPatient()));				
			}
		}
		List<PatientDTO> unique = new ArrayList<>();
        for(PatientDTO patient : patientsDTO){
        	boolean hasPatientWithEmail = false;
        	for(PatientDTO p : unique){
                if(p.getEmail().equals(patient.getEmail())){
                	hasPatientWithEmail = true;
                }
            }
            if(!hasPatientWithEmail){
                unique.add(patient);
            } else {
            	List<PatientDTO> overwritten = new ArrayList<>();

                for(PatientDTO test : unique){
                    if(test.getEmail().equals(patient.getEmail())){
                        overwritten.add(patient);
                    } else {
                        overwritten.add(test);
                    }
                }
                unique = overwritten;
            }
        }		
		
		return new ResponseEntity<>(unique, HttpStatus.OK);
	}
		
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/upcomingAppointmentsForPatient")
	public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointmentsForEmployee(@RequestParam("patientUsername") String patientUsername, @RequestParam String employeeUsername) {	
		Patient patient = patientService.findByUsername(patientUsername);
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : emp.getAppointments()) {
			if(appointment.getStatus() == AppointmentStatus.RESERVED && appointment.getPatient().getId().equals(patient.getId()) && !appointment.isDeleted() ) {
				appointmentsDTO.add(new AppointmentDTO(appointment));
			}
		}
		
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
			
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/allAppointmentsForEmployee")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForEmployee( @RequestParam String employeeUsername, @RequestParam String minDate, @RequestParam String maxDate) {	
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		String min = minDate.split("T")[0];
		String max = maxDate.split("T")[0];
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : emp.getAppointments()) {
			if(!appointment.isDeleted() && appointment.getStatus() != AppointmentStatus.AVAILABLE) {
				boolean afterMin = appointment.getTimePeriod().getStartDate().isAfter(LocalDate.parse(min));
				boolean equalMin = appointment.getTimePeriod().getStartDate().isEqual(LocalDate.parse(min));
				boolean beforeMax = appointment.getTimePeriod().getEndDate().isBefore(LocalDate.parse(max));
				boolean equalMax = appointment.getTimePeriod().getEndDate().isEqual(LocalDate.parse(max));
				if(appointment.getEmployee().getId().equals(emp.getId()) 
						&& (afterMin || equalMin) && (beforeMax || equalMax) && !appointment.isDeleted()) {
					appointmentsDTO.add(new AppointmentDTO(appointment));
				}
			}
		}
		
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/finishAppointment")
	public ResponseEntity<Void> finishAppointment(  @RequestParam String appointmentId, @RequestParam String report) {	
		Appointment appointment = appointmentService.findOne(Long.parseLong(appointmentId));
		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		appointment.setReport(report);
		appointment.setStatus(AppointmentStatus.CONCLUDED);
		appointment = appointmentService.save(appointment);
		return new ResponseEntity<>( HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/patientDidntShowUp")
	public ResponseEntity<Void> patientDidntShowUp(@RequestParam String appointmentId) {	
		Appointment appointment = appointmentService.findOne(Long.parseLong(appointmentId));
		if (appointment == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		appointment.setStatus(AppointmentStatus.PENALED);
		appointmentService.save(appointment);
		
		Patient patient = patientService.findOne(appointment.getPatient().getId());
		patient.setPenaltyPoints(patient.getPenaltyPoints() + 1);
		patientService.save(patient);
		
		return new ResponseEntity<>( HttpStatus.OK);
	}
		
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/getAvailableDermAppointments")
	public ResponseEntity<List<AppointmentDTO>> getAvailableDermAppointments(@RequestParam String employeeUsername,@RequestParam String pharmacyId){
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		
		List<AppointmentDTO> avail = new ArrayList<AppointmentDTO>();
		for (Appointment appointment : emp.getAppointments()) {
			if(appointment.getPharmacy().getId()==Long.parseLong(pharmacyId) && appointment.getStatus()==AppointmentStatus.AVAILABLE && !appointment.isDeleted()) {
				avail.add(new AppointmentDTO(appointment));
			}
		}
		return new ResponseEntity<>(avail, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/createNewAppointmentByEmployee")
	public ResponseEntity<String> createNewAppointmentByEmployee(@RequestParam String employeeUsername, @RequestParam String patientUsername,
			@RequestParam String pharmacyId, @RequestParam String startDate, @RequestParam String startTime,@RequestParam String userType){
		
		Appointment appointment = new Appointment();
		TimePeriod tp = new TimePeriod(LocalDate.parse(startDate), LocalTime.parse(startTime), LocalDate.parse(startDate), LocalTime.parse(startTime).plusHours(1));
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		Patient patient = patientService.findByUsername(patientUsername);
		Pharmacy pharmacy = pharmacyService.findOne(Long.parseLong(pharmacyId));
		Employment employment = employmentService.findOneByEmployeeIdAndPharmacyId(emp.getId(), Long.parseLong(pharmacyId));
		
		String free = "Free";
		if (!employment.getWorkTime().getStartTime().isBefore(LocalTime.parse(startTime)) || !employment.getWorkTime().getEndTime().isAfter(LocalTime.parse(startTime).plusHours(1))) {
			free = "Chosen time not in work hours."; // ne upada u radno vrijeme
		}
		
		//za zaposlenog
		boolean empHasAppThen = appointmentService.checkEmployeeAppointments(tp, emp, false);
		if(empHasAppThen) {
			free = "You already have an appointment then.";
		}
		
		// preklapanje sa postojecim odsustvom
		boolean empIsAbsent = absenceService.checkEmployeeAbsences(tp, emp, pharmacyId);
		if(empIsAbsent) {
			free = "You will be absent then.";
		}
		
		// za pacijenta
		boolean patHasAppThen = appointmentService.checkPatientAppointments(tp, patientUsername);
		if(patHasAppThen) {
			free = "Patient already has an appointment then.";
		}
		
		if(free=="Free") {
			appointment.setEmployee(emp);
			appointment.setPatient(patient);
			appointment.setPharmacy(pharmacy);
			appointment.setTimePeriod(tp);
			appointment.setDeleted(false);
			appointment.setStatus(AppointmentStatus.RESERVED);
			appointment.setPrice(pharmacy.getAppointmentPriceCatalog().getExaminationPrice());			
			
			if(userType.equals("DERMATOLOGIST")) {
				appointment.setType(AppointmentType.DERMATOLOGIST_EXAMINATION);
			}
			if(userType.equals("PHARMACIST")) {
				appointment.setType(AppointmentType.PHARMACIST_CONSULTATION);
			}
			
			appointment = appointmentService.save(appointment);
			
			String emailBody = "This email is confirmation that "+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() 
			+ " has successfully booked an appointment with you at "+ appointment.getTimePeriod().getStartDate() + " " + appointment.getTimePeriod().getStartTime();
			
			EmailContent email = new EmailContent("Appointment booked", emailBody);
			email.addRecipient(appointment.getPatient().getEmail());
	        emailService.sendEmail(email);
			
		}
		return new ResponseEntity<>(free, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/bookAvailableAppointment")
	public ResponseEntity<String> bookAvailableAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId){
		Appointment appointment = appointmentService.findOne(appointmentId);
		
		String free = "Free";
		if(appointment.getStatus()!= AppointmentStatus.AVAILABLE || appointment.isDeleted()) {
			free = "Appointment is unavailable.";
		}
		
		// za pacijenta
		boolean patHasAppThen = appointmentService.checkPatientAppointments(appointment.getTimePeriod(), patientUsername);
		if(patHasAppThen) {
			free = "Patient already has an appointment then.";
		}
		
		if(free == "Free") {
			appointment.setPatient(patientService.findByUsername(patientUsername));
			appointment.setStatus(AppointmentStatus.RESERVED);
			appointment = appointmentService.save(appointment);
			String emailBody = "This email is confirmation that "
			+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() 
			+ " has successfully booked an appointment with you at "
			+ appointment.getTimePeriod().getStartDate() + " " 
			+ appointment.getTimePeriod().getStartTime();
			
			EmailContent email = new EmailContent("Appointment booked", emailBody);
			email.addRecipient(appointment.getPatient().getEmail());
	        emailService.sendEmail(email);
		}
		return new ResponseEntity<>(free, HttpStatus.OK);
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
