package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;
import mrsisa12.pharmacy.service.AbsenceService;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.DermatologistService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.EmploymentService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacistService;
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
	@PostMapping(value = "/savePharmacistAppointment", consumes = "application/json")
	public ResponseEntity<AppointmentDTO> savePharmacistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
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
		appointment = appointmentService.save(appointment);
		employeeService.save(employee); //idk da l ovo treba

		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<AppointmentDTO> saveDermatologistAppointment(@RequestBody AppointmentDTO appointmentDTO) {
		// prilikom kreiranja bih trebao samo da proslijedim id zaposlenog... ID
		// pacijenta ce se naknadno ubacivati i proglasavati termin zauzetim
		Appointment appointment = new Appointment();

		// Trebace vjerovatno da se nesto uradi sa datumom.
		// appointment.setTimePeriod(appointmentDTO.getTimePeriodDTO());
		boolean res = employeeService.checkAppointmentTime(new TimePeriod(appointmentDTO.getTimePeriod()),
				appointmentDTO.getEmployee().getId());
		if (!res) {
			return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
		}
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
	public ResponseEntity<AppointmentDTO> reserveAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId) {

		Appointment appointment = appointmentService.findOne(appointmentId);
		appointment.setPatient(patientService.findByUsername(patientUsername));
		appointment.setStatus(AppointmentStatus.RESERVED);
		appointment = appointmentService.save(appointment);
		
		// email!
		String emailBody = "This email is confirmation that you have successfully booked your appointment with " 
		+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() + " at "
		+ appointment.getTimePeriod().getStartDate() + " " 
		+ appointment.getTimePeriod().getStartTime();
		
		EmailContent email = new EmailContent("Appointment booked", emailBody);
		email.addRecipient(appointment.getPatient().getEmail());
        emailService.sendEmail(email);


		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
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
	
	@GetMapping(value = "/searchPatients")
	public ResponseEntity<List<PatientDTO>> searchPatients(@RequestParam String patientFirstName, @RequestParam String patientLastName, @RequestParam String employeeUsername) {	
		Employee emp = employeeService.findOneByUsername(employeeUsername);
		List<Appointment> appointments = appointmentService.findAll();
		
		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			if(appointment.getEmployee().getId().equals(emp.getId()) && appointment.getStatus() == AppointmentStatus.RESERVED) {
				if(patientLastName == "" && patientFirstName != "" && appointment.getPatient().getFirstName().toLowerCase().contains(patientFirstName.toLowerCase())) {
					patientsDTO.add(new PatientDTO(appointment.getPatient()));	
				}
				else if(patientFirstName == "" && patientLastName != "" && appointment.getPatient().getLastName().toLowerCase().contains(patientLastName.toLowerCase())) {
					patientsDTO.add(new PatientDTO(appointment.getPatient()));		
				}
				else if(patientLastName != ""  && patientFirstName != ""
						 && appointment.getPatient().getFirstName().toLowerCase().contains(patientFirstName.toLowerCase()) 
						 && appointment.getPatient().getLastName().toLowerCase().contains(patientLastName.toLowerCase())) {
					patientsDTO.add(new PatientDTO(appointment.getPatient()));		
				}
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
	
	@GetMapping(value = "/allAppointmentsForEmployee")
	public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForEmployee( @RequestParam String employeeUsername, @RequestParam String minDate, @RequestParam String maxDate) {	
		Employee emp = employeeService.findOneByUsername(employeeUsername);
		List<Appointment> appointments = appointmentService.findAll();
		String min = minDate.split("T")[0];
		String max = maxDate.split("T")[0];
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			boolean afterMin = appointment.getTimePeriod().getStartDate().isAfter(LocalDate.parse(min));
			boolean equalMin = appointment.getTimePeriod().getStartDate().isEqual(LocalDate.parse(min));
			boolean beforeMax = appointment.getTimePeriod().getEndDate().isBefore(LocalDate.parse(max));
			boolean equalMax = appointment.getTimePeriod().getEndDate().isEqual(LocalDate.parse(max));
			if(appointment.getEmployee().getId().equals(emp.getId()) && appointment.getStatus() != AppointmentStatus.AVAILABLE
					&& (afterMin || equalMin) && (beforeMax || equalMax) && !appointment.isDeleted()) {
				appointmentsDTO.add(new AppointmentDTO(appointment));
			}
		}
		
		return new ResponseEntity<>(appointmentsDTO, HttpStatus.OK);
	}
	
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
	
	@GetMapping(value = "/createNewAppointmentByEmployee")
	public ResponseEntity<Boolean> createNewAppointmentByEmployee(@RequestParam String employeeUsername, @RequestParam String patientUsername,
			@RequestParam String pharmacyId, @RequestParam String startDate, @RequestParam String startTime,@RequestParam String userType){
		
		Appointment appointment = new Appointment();
		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalDate endDate2 = LocalDate.parse(startDate);
		LocalTime startTime2 = LocalTime.parse(startTime);
		LocalTime endTime2 = LocalTime.parse(startTime).plusHours(1);
		TimePeriod tp = new TimePeriod(startDate2, startTime2, endDate2, endTime2);
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		Patient patient = patientService.findByUsername(patientUsername);
		Pharmacy pharmacy = pharmacyService.findOne(Long.parseLong(pharmacyId));
		Employment employment = employmentService.findOneByEmployeeIdAndPharmacyId(emp.getId(), Long.parseLong(pharmacyId));
		List<Absence> absences = absenceService.findAllByEmployeeId(emp.getId());
		
		boolean free = true; 
		if (!employment.getWorkTime().getStartTime().isBefore(startTime2) || !employment.getWorkTime().getEndTime().isAfter(endTime2)) {
			free = false; // ne upada u radno vrijeme
		}
		
		// Ovdje se mora provjeravati i datum i vrijeme za zaposlenog
		for (Appointment appo : emp.getAppointments()) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(endDate2.atTime(endTime2))
					&& eWorkTSDateTime.isAfter(startDate2.atTime(startTime2)))
					&& !(eWorkTEDateTime.isBefore(endDate2.atTime(endTime2))
							&& eWorkTEDateTime.isBefore(startDate2.atTime(startTime2)))) {
				if(!(userType.equals("DERMATOLOGIST") && appo.getStatus()==AppointmentStatus.AVAILABLE)) {
					free = false;  // preklapanje sa postojecim terminom
					break;
				}
			}
		}
		for (Absence absence : absences) {
			if(absence.getStatus()!= AbsenceStatus.DENIED) {
				LocalDateTime eWorkTSDateTime = absence.getTimePeriod().getStartDate().atTime(absence.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = absence.getTimePeriod().getEndDate().atTime(absence.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(endDate2.atTime(LocalTime.parse("00:00:00")))
						&& eWorkTSDateTime.isAfter(startDate2.atTime(LocalTime.parse("00:00:00"))))
						&& !(eWorkTEDateTime.isBefore(endDate2.atTime(LocalTime.parse("00:00:00")))
								&& eWorkTEDateTime.isBefore(startDate2.atTime(LocalTime.parse("00:00:00"))))) {
						free = false;  // preklapanje sa postojecim odsustvom
						break;				
				}
			}
		}
		if(free) {
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
			
			String emailBody = "This email is confirmation that "
			+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() 
			+ " has successfully booked an appointment with you at "
			+ appointment.getTimePeriod().getStartDate() + " " 
			+ appointment.getTimePeriod().getStartTime();
			
			EmailContent email = new EmailContent("Appointment booked", emailBody);
			email.addRecipient(appointment.getPatient().getEmail());
	        emailService.sendEmail(email);
			
			return new ResponseEntity<>(true, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(false, HttpStatus.OK);
		}
	}
	
	@GetMapping(value = "/checkAvailableAppointmentByEmployee")
	public ResponseEntity<String> checkAvailableAppointmentByEmployee(@RequestParam String employeeUsername, @RequestParam String patientUsername,
			@RequestParam String pharmacyId, @RequestParam("startDate") String startDate, @RequestParam String startTime, @RequestParam String userType){
		
		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalDate endDate2 = LocalDate.parse(startDate);
		LocalTime startTime2 = LocalTime.parse(startTime);
		LocalTime endTime2 = LocalTime.parse(startTime).plusHours(1);
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		Employment employment = employmentService.findOneByEmployeeIdAndPharmacyId(emp.getId(), Long.parseLong(pharmacyId));
		List<Appointment> patientAppointments = appointmentService.findAllByPatientUsername(patientUsername);
		List<Absence> absences = absenceService.findAllByEmployeeId(emp.getId());
		
		String free = "Free";
		if (!employment.getWorkTime().getStartTime().isBefore(startTime2) || !employment.getWorkTime().getEndTime().isAfter(endTime2)) {
			free = "Chosen time not in work hours."; // ne upada u radno vrijeme
		}
		
		// Ovdje se mora provjeravati i datum i vrijeme za zaposlenog
		for (Appointment appo : emp.getAppointments()) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(endDate2.atTime(endTime2))
					&& eWorkTSDateTime.isAfter(startDate2.atTime(startTime2)))
					&& !(eWorkTEDateTime.isBefore(endDate2.atTime(endTime2))
							&& eWorkTEDateTime.isBefore(startDate2.atTime(startTime2)))) {
				if(!(userType.equals("DERMATOLOGIST") && appo.getStatus()==AppointmentStatus.AVAILABLE)) {
					free = "You already have an appointment then.";  // preklapanje sa postojecim terminom
					break;
				}
			}
		}
		for (Absence absence : absences) {
			if(absence.getStatus()!= AbsenceStatus.DENIED) {
				LocalDateTime eWorkTSDateTime = absence.getTimePeriod().getStartDate().atTime(absence.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = absence.getTimePeriod().getEndDate().atTime(absence.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(endDate2.atTime(LocalTime.parse("00:00:00")))
						&& eWorkTSDateTime.isAfter(startDate2.atTime(LocalTime.parse("00:00:00"))))
						&& !(eWorkTEDateTime.isBefore(endDate2.atTime(LocalTime.parse("00:00:00")))
								&& eWorkTEDateTime.isBefore(startDate2.atTime(LocalTime.parse("00:00:00"))))) {
						free = "You will be absent then.";  // preklapanje sa postojecim odsustvom
						break;				
				}
			}
		}
		
		// za pacijenta
		for (Appointment appo : patientAppointments) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(endDate2.atTime(endTime2))
					&& eWorkTSDateTime.isAfter(startDate2.atTime(startTime2)))
					&& !(eWorkTEDateTime.isBefore(endDate2.atTime(endTime2))
							&& eWorkTEDateTime.isBefore(startDate2.atTime(startTime2)))) {
				free = "Patient already has an appointment then."; // preklapanje sa postojecim terminom
				break;
			}
		}
		
		return new ResponseEntity<>(free, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/bookAvailableAppointment")
	public ResponseEntity<String> bookAvailableAppointment(@RequestParam String patientUsername, @RequestParam Long appointmentId){
		Appointment appointment = appointmentService.findOne(appointmentId);
		List<Appointment> patientAppointments = appointmentService.findAllByPatientUsername(patientUsername);
		
		String free = "Free";
		if(appointment.getStatus()!= AppointmentStatus.AVAILABLE || appointment.isDeleted()) {
			free = "Appointment is unavailable.";
		}
		
		// za pacijenta		
		for (Appointment appo : patientAppointments) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime()))
					&& eWorkTSDateTime.isAfter(appointment.getTimePeriod().getStartDate().atTime(appointment.getTimePeriod().getStartTime())))
					&& !(eWorkTEDateTime.isBefore(appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime()))
							&& eWorkTEDateTime.isBefore(appointment.getTimePeriod().getStartDate().atTime(appointment.getTimePeriod().getStartTime())))) {
				free = "Patient already has an appointment then."; // preklapanje sa postojecim terminom
				break;
			}
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
	
}
