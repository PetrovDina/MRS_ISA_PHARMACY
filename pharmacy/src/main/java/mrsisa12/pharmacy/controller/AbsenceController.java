package mrsisa12.pharmacy.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.AbsenceDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AbsenceType;
import mrsisa12.pharmacy.service.AbsenceService;
import mrsisa12.pharmacy.service.AppointmentService;
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
	
	@Autowired
	private AppointmentService appointmentService;

	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
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
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/create")
	public ResponseEntity<Boolean> createAbsence(@RequestParam String pharmacyId, @RequestParam String startDate,
			@RequestParam String endDate, @RequestParam String employeeUsername, @RequestParam String absenceType ){
		Employee emp = employeeService.findOneByUsernameWithAppointments(employeeUsername);
		LocalDate startDate2 = LocalDate.parse(startDate);
		LocalDate endDate2 = LocalDate.parse(endDate);
		TimePeriod tp = new TimePeriod(startDate2, LocalTime.parse("00:00:00"), endDate2, LocalTime.parse("00:00:00"));
		
		boolean free = true;
		// Ovdje se mora provjeravati i datum i vrijeme za zaposlenog
		boolean empHasAppThen = appointmentService.checkEmployeeAppointments(tp, emp, true);
		if(empHasAppThen) {
			free = false;
		}
		
		// preklapanje sa postojecim odsustvom
		boolean empIsAbsent = absenceService.checkEmployeeAbsences(tp, emp, pharmacyId);
		if(empIsAbsent) {
			free = false;
		}
		
		if(free) {
			Pharmacy pharmacy = pharmacyService.findOne(Long.parseLong(pharmacyId));
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
		
		boolean isFree;
		try {
			isFree = absenceService.acceptAbsence(absenceDTO);
		} catch (ObjectOptimisticLockingFailureException e) {
			isFree = false;
		}
		return new ResponseEntity<>(isFree, HttpStatus.CREATED);
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
