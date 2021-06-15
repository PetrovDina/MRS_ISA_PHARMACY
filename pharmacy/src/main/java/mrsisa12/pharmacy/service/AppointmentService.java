package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

import mrsisa12.pharmacy.repository.AppointmentRepository;

@Service
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AbsenceService absenceService;
	
	@Autowired
	private EmploymentService employmentService;

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

	public List<Appointment> findAllConcludedByPatientAndPharmacy(String patientUsername, Long pharmacyId) {
		return appointmentRepository.findAllConcludedByPatientAndPharmacy(patientUsername, pharmacyId);
	}
	
	
	public boolean checkPatientAppointments(TimePeriod tp, String patientUsername) {
		List<Appointment> patientAppointments = appointmentRepository.findAllByPatientUsername(patientUsername);

		// za pacijenta
		for (Appointment appo : patientAppointments) {
			if(appo.getStatus().equals(AppointmentStatus.RESERVED)) {
				LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(tp.getEndDate().atTime(tp.getEndTime()))
						&& eWorkTSDateTime.isAfter(tp.getStartDate().atTime(tp.getStartTime())))
						&& !(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))
								&& eWorkTEDateTime.isBefore(tp.getStartDate().atTime(tp.getStartTime())))) {
					return true;//"Patient already has an appointment then."; preklapanje sa postojecim terminom
				}
			}
		}
		return false;
	}
	
	public boolean checkEmployeeAppointments(TimePeriod tp, Employee emp, boolean check) {
		
		for (Appointment appo : emp.getAppointments()) {
			if(!appo.getStatus().equals(AppointmentStatus.CANCELLED) && !(check == true && appo.getStatus().equals(AppointmentStatus.AVAILABLE))) {
				LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(tp.getEndDate().atTime(tp.getEndTime()))
						&& eWorkTSDateTime.isAfter(tp.getStartDate().atTime(tp.getStartTime())))
						&& !(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))
								&& eWorkTEDateTime.isBefore(tp.getStartDate().atTime(tp.getStartTime())))) {
						return true; //"You already have an appointment then.";   preklapanje sa postojecim terminom
				}
			}
		}
		return false;

	}
	
	public List<Appointment> getAllConcludedAppointmentsForEmployee(String username) {
		return appointmentRepository.getAllConcludedAppointmentsForEmployee(username);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void checkExpiredAppointments() {
		List<Appointment> apps = appointmentRepository.findAllReservedAndAvailable();
		System.out.println("--SETTING APPOINTMENT STATUS TO EXPIRED--");
		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now());

		for (Appointment appo: apps) {
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))) {
				appo.setStatus(AppointmentStatus.EXPIRED);
				appointmentRepository.save(appo);
			}
		}
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String reserveAppointment(String patientUsername, Long appointmentId) {
		
		Patient patient = patientService.findByUsername(patientUsername);
		
		Appointment appointment = findOne(appointmentId);
		
		// provjera da li je neko zauzeo termin
		if(appointment.getStatus() == AppointmentStatus.RESERVED) {
			return "Reservation failed, try again later.";
		}
		
		appointment.setPatient(patient);
		appointment.setStatus(AppointmentStatus.RESERVED);
		
		
		Double price = appointment.getPrice();
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		String message = loyaltyProgramService.generateAppointmentMessage(patient, price, pointsForPatient);
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		appointment = save(appointment);
		
		emailService.sendEmailToPatient(appointment, patient);
		
		return message;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public String bookPharmacistAppointment(AppointmentDTO appointmentDTO) {
		
		Appointment appointment = new Appointment();
		
		employeeService.findOneEmployee(appointmentDTO.getEmployee().getId());
		
		Employee employee = employeeService.findOneWithAllAppointments(appointmentDTO.getEmployee().getId());
		
		TimePeriod tp = new TimePeriod(appointmentDTO.getTimePeriod());
		
		for (Appointment appo : employee.getAppointments()) {
			if(appo.getStatus() == AppointmentStatus.RESERVED) {
				LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(tp.getEndDate().atTime(tp.getEndTime()))
						&& eWorkTSDateTime.isAfter(tp.getStartDate().atTime(tp.getStartTime())))
						&& !(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))
								&& eWorkTEDateTime.isBefore(tp.getStartDate().atTime(tp.getStartTime())))) {
						return "You already have an appointment then.";
				}
			}
		}
		
		appointment.setEmployee(employee);
		
		appointment.setTimePeriod(new TimePeriod(appointmentDTO.getTimePeriod()));
		appointment.getTimePeriod().setEndTime(appointment.getTimePeriod().getStartTime().plusHours(1));
		appointment.setStatus(AppointmentStatus.RESERVED); //rezervisemo ga!
		appointment.setDeleted(false);
		
		//postavljamo pacijenta na termin
		Patient patient = patientService.findByUsername(appointmentDTO.getPatient().getUsername());
		appointment.setPatient(patient);
		
		Pharmacy pharmacy = pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
		
		appointment.setPharmacy(pharmacy);
		appointment.setType(AppointmentType.PHARMACIST_CONSULTATION);
		
		Double price = pharmacy.getAppointmentPriceCatalog().getConsultationPrice();
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);
		
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		String message = loyaltyProgramService.generateAppointmentMessage(patient, price, pointsForPatient);
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		save(appointment);
		
		return message;
	}

	@Transactional
	public Appointment createDermatologistAppointment(AppointmentDTO appointmentDTO) {
		
		Appointment appointment = new Appointment();
		
		Employee employee = employeeService.findOneEmployee(appointmentDTO.getEmployee().getId()); // da dobijemo blokadu
		Pharmacy pharmacy = pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
		
		appointment.setTimePeriod(new TimePeriod(appointmentDTO.getTimePeriod()));
		boolean res = employeeService.checkAppointmentTime(new TimePeriod(appointmentDTO.getTimePeriod()),
				appointmentDTO.getEmployee().getId(), pharmacy);
		if (!res) {
			// nije moguce kreirati tada termin
			appointment.setId(-1L);
			return appointment;
		}
		// postavljamo vrijeme i datum
		appointment.setTimePeriod(new TimePeriod(appointmentDTO.getTimePeriod()));
		// postavljamo status da je dostupan jer se kreira
		appointment.setStatus(AppointmentStatus.AVAILABLE);
		// postavljamo da je neobrisan
		appointment.setDeleted(false);
		// postavljamo dermatologa na termin
		appointment.setEmployee(employee);
		appointment.setInProgress(false);
		
		appointment.setPatient(null);
		
		// postavljanje cijene pregleda kod dermatologa
		appointment.setPrice(pharmacy.getAppointmentPriceCatalog().getExaminationPrice());
		
		// postavljanje apoteke
		appointment.setPharmacy(pharmacy);

		appointment.setType(AppointmentType.DERMATOLOGIST_EXAMINATION);
		appointment = save(appointment);

		return appointment;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String setAppointmentInProgress( Long appointmentId) {
		Appointment appointment = findOneWithEmployee(appointmentId);
		Appointment apps = findAllInProgressByEmployeeId(appointment.getEmployee().getId());
				
		if(apps != null){
			return "An appointment is already in progress.";
		}	
		else{
			if (appointment != null && appointment.isDeleted()) {
				return "No such appointment.";
			}else {
				appointment.setInProgress(true);
				appointment = save(appointment);
				return "ok";
			}	
		}
					
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String patientDidntShowUp( Long appointmentId) {
		Appointment appointment = findOne(appointmentId);
		if (appointment == null || appointment.isDeleted()) {
			return "No such appointment.";
		}
		else if(appointment.isInProgress()) {
			return "Appointment is already in progress.";
		}else {
			appointment.setStatus(AppointmentStatus.PENALED);
			save(appointment);
			
			Patient patient = patientService.findOne(appointment.getPatient().getId());
			patient.setPenaltyPoints(patient.getPenaltyPoints() + 1);
			patientService.save(patient);
			return "ok";
		}
	}
	
	public Appointment findAllInProgressByEmployeeId( Long employeeId) {
		return appointmentRepository.findAllInProgressByEmployeeId(employeeId);
	}
	
	public Appointment findOneWithEmployee(Long employeeId) {
		return appointmentRepository.findOneWithEmployee(employeeId);
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String bookAvailableAppointment(String patientUsername, Long appointmentId) {
		
		Patient patient = patientService.findByUsername(patientUsername);
		
		Appointment appointment = findOne(appointmentId);
		
		// provjera da li je neko zauzeo termin
		if(appointment.getStatus() == AppointmentStatus.RESERVED || appointment.isDeleted()) {
			return "Reservation failed, try again later.";
		}
		
		boolean patHasAppThen = checkPatientAppointments(appointment.getTimePeriod(), patientUsername);
		if(patHasAppThen) {
			return "Patient already has an appointment then.";
		}
		
		appointment.setPatient(patient);
		appointment.setStatus(AppointmentStatus.RESERVED);
		
		
		Double price = appointment.getPrice();
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		
		appointment = save(appointment);
		
		String emailBody = "This email is confirmation that "
		+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() 
		+ " has successfully booked an appointment with you at "
		+ appointment.getTimePeriod().getStartDate() + " " 
		+ appointment.getTimePeriod().getStartTime();
		
		EmailContent email = new EmailContent("Appointment booked", emailBody);
		email.addRecipient(appointment.getPatient().getEmail());
        emailService.sendEmail(email);
		
		return "ok";
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public String createNewAppointmentByEmployee(AppointmentDTO appointmentDTO) {
		
		Appointment appointment = new Appointment();
				
		Employee employee = employeeService.findOneByUsernameWithAppointments(appointmentDTO.getEmployee().getUsername());
		Employment employment = employmentService.findOneByEmployeeIdAndPharmacyId(employee.getId(), appointmentDTO.getPharmacy().getId());
		
		TimePeriod tp = new TimePeriod(appointmentDTO.getTimePeriod());
		tp.setEndTime( tp.getStartTime().plusHours(1));
		
		if (!employment.getWorkTime().getStartTime().isBefore(tp.getStartTime()) || !employment.getWorkTime().getEndTime().isAfter(tp.getEndTime())) {
			return "Chosen time not in work hours."; // ne upada u radno vrijeme
		}
		
		//za zaposlenog
		boolean empHasAppThen = checkEmployeeAppointments(tp, employee, false);
		if(empHasAppThen) {
			return  "You already have an appointment then.";
		}
		
		// preklapanje sa postojecim odsustvom
		boolean empIsAbsent = absenceService.checkEmployeeAbsences(tp, employee, appointmentDTO.getPharmacy().getId().toString());
		if(empIsAbsent) {
			return "You will be absent then.";
		}
		
		// za pacijenta
		boolean patHasAppThen = checkPatientAppointments(tp, appointmentDTO.getPatient().getUsername());
		if(patHasAppThen) {
			return "Patient already has an appointment then.";
		}
				
		appointment.setEmployee(employee);		
		appointment.setTimePeriod(tp);
		appointment.setStatus(AppointmentStatus.RESERVED); //rezervisemo ga!
		appointment.setDeleted(false);
		appointment.setInProgress(false);
		
		//postavljamo pacijenta na termin
		Patient patient = patientService.findByUsername(appointmentDTO.getPatient().getUsername());
		appointment.setPatient(patient);
		
		Pharmacy pharmacy = pharmacyService.findOne(appointmentDTO.getPharmacy().getId());
		
		appointment.setPharmacy(pharmacy);
		appointment.setType(appointmentDTO.getType());
		
		Double price = 0.0;
		if(appointmentDTO.getType() == AppointmentType.DERMATOLOGIST_EXAMINATION) {
			price = pharmacy.getAppointmentPriceCatalog().getExaminationPrice();
		}else {
			price = pharmacy.getAppointmentPriceCatalog().getConsultationPrice();
		}
		price = loyaltyProgramService.getFinalAppointmentPrice(price, patient);		
		appointment.setPrice(price);
		
		Integer pointsForPatient = loyaltyProgramService.appointmentPoints();
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);		
		save(appointment);
		
		String emailBody = "This email is confirmation that "+ appointment.getEmployee().getFirstName() + " " + appointment.getEmployee().getLastName() 
		+ " has successfully booked an appointment with you at "+ appointment.getTimePeriod().getStartDate() + " " + appointment.getTimePeriod().getStartTime();
		
		EmailContent email = new EmailContent("Appointment booked", emailBody);
		email.addRecipient(appointment.getPatient().getEmail());
        emailService.sendEmail(email);
		
		return "Free";
	}
	
	public List<AppointmentDTO> getUpcomingAppointmentsForEmployee(String patientUsername,  String employeeUsername) {			
		Patient patient = patientService.findByUsername(patientUsername);
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now());
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : emp.getAppointments()) {
			LocalDateTime eWorkTEDateTime = appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime());
			if(appointment.getStatus() == AppointmentStatus.RESERVED && appointment.getPatient().getId().equals(patient.getId()) && !appointment.isDeleted() ) {
				if(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))) {
					appointment.setStatus(AppointmentStatus.EXPIRED);
					save(appointment);
				}else
					appointmentsDTO.add(new AppointmentDTO(appointment));
			}
		}
		
		return appointmentsDTO;
	}
	
	public List<AppointmentDTO> getAllAppointmentsForEmployee(  String employeeUsername,  String minDate,  String maxDate) {	
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		String min = minDate.split("T")[0];
		String max = maxDate.split("T")[0];
		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now());
		
		List<AppointmentDTO> appointmentsDTO = new ArrayList<>();
		for (Appointment appointment : emp.getAppointments()) {
			appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime());
			if(!appointment.isDeleted() && appointment.getStatus() != AppointmentStatus.AVAILABLE) {
				boolean afterMin = appointment.getTimePeriod().getStartDate().isAfter(LocalDate.parse(min));
				boolean equalMin = appointment.getTimePeriod().getStartDate().isEqual(LocalDate.parse(min));
				boolean beforeMax = appointment.getTimePeriod().getEndDate().isBefore(LocalDate.parse(max));
				boolean equalMax = appointment.getTimePeriod().getEndDate().isEqual(LocalDate.parse(max));
				LocalDateTime eWorkTEDateTime = appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime());
				if(appointment.getEmployee().getId().equals(emp.getId()) 
						&& (afterMin || equalMin) && (beforeMax || equalMax)) {
					if(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime())) && appointment.getStatus() == AppointmentStatus.RESERVED) {
						appointment.setStatus(AppointmentStatus.EXPIRED);
						save(appointment);
					}
					appointmentsDTO.add(new AppointmentDTO(appointment));
				}
			}
		}
		
		return appointmentsDTO;
	}
	
	public List<PatientDTO> getUpcomingPatientsForEmployee( String username) {	
		Employee emp = employeeService.findOneByUsernameWithAppointments(username);
		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now());
		
		List<PatientDTO> patientsDTO = new ArrayList<>();
		for (Appointment appointment : emp.getAppointments()) {
			LocalDateTime eWorkTEDateTime = appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime());
			if(appointment.getStatus() == AppointmentStatus.RESERVED && !appointment.isDeleted()) {
				if(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))) {
					appointment.setStatus(AppointmentStatus.EXPIRED);
					save(appointment);
				}else
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
		
		return unique;
	}
	
	public List<AppointmentDTO> getAvailableDermAppointments( String employeeUsername, String pharmacyId){
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now());
		
		List<AppointmentDTO> avail = new ArrayList<AppointmentDTO>();
		for (Appointment appointment : emp.getAppointments()) {
			LocalDateTime eWorkTEDateTime = appointment.getTimePeriod().getEndDate().atTime(appointment.getTimePeriod().getEndTime());
			if(appointment.getPharmacy().getId()==Long.parseLong(pharmacyId) && appointment.getStatus()==AppointmentStatus.AVAILABLE && !appointment.isDeleted()) {
				if(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))) {
					appointment.setStatus(AppointmentStatus.EXPIRED);
					save(appointment);
				}else
					avail.add(new AppointmentDTO(appointment));
			}
		}
		return avail;
	}

}