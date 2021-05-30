package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.AbsenceDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AbsenceType;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.service.AbsenceService;
import mrsisa12.pharmacy.service.EmployeeService;
import mrsisa12.pharmacy.service.PharmacyService;

@RestController
@RequestMapping("/absences")
public class AbsenceController {

	@Autowired
	private AbsenceService absenceService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@GetMapping(value = "/allAbsencesForEmployee")
	public ResponseEntity<List<AbsenceDTO>> getAllAbsencesForEmployee(@RequestParam String username) {	
		Employee emp = employeeService.findOneByUsername(username);
		List<Absence> absences = absenceService.findAllByEmployeeId(emp.getId());
		
		List<AbsenceDTO> absencesDTO = new ArrayList<>();
		for (Absence abs : absences) {
			absencesDTO.add(new AbsenceDTO(abs));
		}	
		
		return new ResponseEntity<>(absencesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/allRequestedAbsencesForEmployee")
	public ResponseEntity<List<AbsenceDTO>> getAllRequestedAbsencesForEmployee(@RequestParam String username) {	
		Employee emp = employeeService.findOneByUsername(username);
		List<Absence> absences = absenceService.findAllRequestedByEmployeeId(emp.getId());
		
		List<AbsenceDTO> absencesDTO = new ArrayList<>();
		for (Absence abs : absences) {
			absencesDTO.add(new AbsenceDTO(abs));
		}	
		
		return new ResponseEntity<>(absencesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/create")
	public ResponseEntity<Boolean> createAbsence(@RequestParam String pharmacyId, @RequestParam String startDate,
			@RequestParam String endDate, @RequestParam String employeeUsername, @RequestParam String absenceType ){
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		List<Absence> absences = absenceService.findAllByEmployeeId(emp.getId());
		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalDate endDate2 = LocalDate.parse(endDate);
		
		boolean free = true;
		for (Appointment appo : emp.getAppointments()) {
			LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(endDate2.atTime(LocalTime.parse("00:00:00")))
					&& eWorkTSDateTime.isAfter(startDate2.atTime(LocalTime.parse("00:00:00"))))
					&& !(eWorkTEDateTime.isBefore(endDate2.atTime(LocalTime.parse("00:00:00")))
							&& eWorkTEDateTime.isBefore(startDate2.atTime(LocalTime.parse("00:00:00"))))) {
					free = false;  // preklapanje sa postojecim terminom
					break;				
			}
		}
		
		for (Absence absence : absences) {
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
		
		if(free) {
			Pharmacy pharmacy = pharmacyService.findOne(Long.parseLong(pharmacyId));
			TimePeriod tp = new TimePeriod(startDate2, LocalTime.parse("00:00:00"), endDate2, LocalTime.parse("00:00:00"));
			Absence abs = new Absence();
			abs.setStatus(AbsenceStatus.REQUESTED);
			abs.setTimePeriod(tp);
			abs.setPharmacy(pharmacy);
			abs.setEmployee(emp);
			abs.setType(AbsenceType.valueOf(absenceType));			
			absenceService.save(abs);
		}
		
		return new ResponseEntity<>(free ,HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/accept")
	public ResponseEntity<Boolean> acceptAbsence(@RequestBody AbsenceDTO absenceDTO){
		Employee emp = employeeService.findOneByUsernameWithAppointments(absenceDTO.getEmployee().getUsername());
		LocalDate startDate2 = LocalDate.parse(absenceDTO.getTimePeriod().getStartDate());
		LocalDate endDate2 = LocalDate.parse(absenceDTO.getTimePeriod().getEndDate());
		LocalDate bookedAppointment = null;
		boolean free = true;
		for (Appointment appo : emp.getAppointments()) {
			if(appo.getStatus().equals(AppointmentStatus.RESERVED)) {
				LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(endDate2.atTime(LocalTime.parse("00:00:00")))
						&& eWorkTSDateTime.isAfter(startDate2.atTime(LocalTime.parse("00:00:00"))))
						&& !(eWorkTEDateTime.isBefore(endDate2.atTime(LocalTime.parse("00:00:00")))
								&& eWorkTEDateTime.isBefore(startDate2.atTime(LocalTime.parse("00:00:00"))))) {
						free = false;  // preklapanje sa postojecim terminom
						bookedAppointment  = LocalDate.of(eWorkTSDateTime.getYear(), eWorkTEDateTime.getMonth(), eWorkTSDateTime.getDayOfMonth());
						break;				
				}
			}
		}
		Absence absence = absenceService.findOne(absenceDTO.getId());
		if(free) {
			absence.setStatus(AbsenceStatus.APPROVED);
			emailService.sendEmailToEmployeeAboutAbsence(absence, "APPROVED", "");
		}
		else {
			absence.setStatus(AbsenceStatus.DENIED);
			emailService.sendEmailToEmployeeAboutAbsence(absence, "REJECTED", 
				"The reason for rejecting your "+ absence.getType() +" request is the booked appointment on " + bookedAppointment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		absenceService.save(absence);
		return new ResponseEntity<>(free ,HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/reject")
	public ResponseEntity<Boolean> rejectAbsence(@RequestBody AbsenceDTO absenceDTO, @RequestParam String rejectionReason){
		Absence absence = absenceService.findOne(absenceDTO.getId());
		absence.setStatus(AbsenceStatus.DENIED);
		emailService.sendEmailToEmployeeAboutAbsence(absence, "REJECTED", 
			"Your request for" + absence.getType() + " has beed rejeceted with following message: \"" + rejectionReason + "\"");
		absenceService.save(absence);
		return new ResponseEntity<>(true, HttpStatus.CREATED);
	}
}
