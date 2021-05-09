package mrsisa12.pharmacy.controller;

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
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Employment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EmployeeService;
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
		
		//dina dodala - TODO izmeni da ne bude fiksno nego da se dobavi prava cena iz cenovnika onda kad se on kreira!
		appointment.setPrice(1500);
		
		appointment.setPharmacy(pharmacyService.findOne(appointmentDTO.getPharmacy().getId()));

		appointment.setType(AppointmentType.PHARMACIST_CONSULTATION);
		appointment = appointmentService.save(appointment);
		employeeService.save(employee); //idk da l ovo treba

		return new ResponseEntity<>(new AppointmentDTO(appointment), HttpStatus.CREATED);
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<AppointmentDTO> saveAppointment(@RequestBody AppointmentDTO appointmentDTO) {
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
		
		//dina dodala - TODO izmeni da ne bude fiksno nego da se dobavi prava cena iz cenovnika onda kad se on kreira!
		appointment.setPrice(1500);
		
		//dina dodala - TODO izmeni da bude apoteka gde radi dermatolog a ne uvek jedinica!!!
		appointment.setPharmacy(pharmacyService.findOne(1l));

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
		
		EmailContent email = new EmailContent("Appointment booked",
                appointment.getPatient().getEmail(), emailBody);
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
		
		return new ResponseEntity<>(filterUniquePatients(patientsDTO), HttpStatus.OK);
	}
	
	private List<PatientDTO> filterUniquePatients(List<PatientDTO> patients) {
        List<PatientDTO> unique = new ArrayList<>();
        for(PatientDTO patient : patients){
            if(!hasPatientWithEmail(unique, patient.getEmail())){
                unique.add(patient);
            } else {
                unique = overwrite(unique, patient);
            }
        }
        return unique;
    }

    private List<PatientDTO> overwrite(List<PatientDTO> unique, PatientDTO patient) {
        List<PatientDTO> overwritten = new ArrayList<>();

        for(PatientDTO test : unique){
            if(test.getEmail().equals(patient.getEmail())){
                overwritten.add(patient);
            } else {
                overwritten.add(test);
            }
        }

        return overwritten;
    }

    private boolean hasPatientWithEmail(List<PatientDTO> unique, String email) {
        for(PatientDTO patient : unique){
            if(patient.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }
	
	@GetMapping(value = "/upcomingAppointmentsForPatient")
	public ResponseEntity<List<AppointmentDTO>> getUpcomingAppointmentsForEmployee(@RequestParam("patientUsername") String patientUsername, @RequestParam String employeeUsername) {	
		Patient patient = patientService.findByUsername(patientUsername);
		Employee emp = employeeService.findOneByUsername(employeeUsername);
		List<Appointment> appointments = appointmentService.findAll();
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : appointments) {
			if(appointment.getEmployee().getId().equals(emp.getId()) && appointment.getPatient().getId().equals(patient.getId()) && appointment.getStatus() == AppointmentStatus.RESERVED) {
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
		
		return new ResponseEntity<>(filterUniquePatients(patientsDTO), HttpStatus.OK);
	}
	
}
