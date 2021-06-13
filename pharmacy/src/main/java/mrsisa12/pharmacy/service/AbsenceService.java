package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.dto.AbsenceDTO;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Absence;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AbsenceStatus;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.repository.AbsenceRepository;

@Service
public class AbsenceService {

	@Autowired
	private AbsenceRepository absenceRepository;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private EmailService emailService;
	
	public Absence findOne(Long id) {
		return absenceRepository.findById(id).orElseGet(null);
	}

	public List<Absence> findAll() {
		return absenceRepository.findAll();
	}

	public Page<Absence> findAll(Pageable page) {
		return absenceRepository.findAll(page);
	}

	public Absence save(Absence absence) {
		return absenceRepository.save(absence);
	}
	
	public List<Absence> findAllByEmployeeId(Long id) {
		return absenceRepository.findAllByEmployeeId(id);
	}

	public List<Absence> findAllByPharmacyId(Long id) {
		return absenceRepository.findAllByPharmacyId(id);
	}
	
	public List<Absence> findAllAprovedAbsencesByEmployeeId(Long id){
		return absenceRepository.findAllAprovedAbsencesByEmployeeId(id);
	}
	
	public boolean checkEmployeeAbsences(TimePeriod tp, Employee emp, String pharmacyId) {
		List<Absence> absences = absenceRepository.findAllAprovedAbsencesByEmployeeIdAndPharmacyId(emp.getId(), Long.parseLong(pharmacyId));
		
		for (Absence absence : absences) {
			LocalDateTime eWorkTSDateTime = absence.getTimePeriod().getStartDate().atTime(absence.getTimePeriod().getStartTime());
			LocalDateTime eWorkTEDateTime = absence.getTimePeriod().getEndDate().atTime(absence.getTimePeriod().getEndTime());
			if (!(eWorkTSDateTime.isAfter(tp.getEndDate().atTime(tp.getEndTime()))
					&& eWorkTSDateTime.isAfter(tp.getStartDate().atTime(tp.getStartTime())))
					&& !(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))
							&& eWorkTEDateTime.isBefore(tp.getStartDate().atTime(tp.getStartTime())))) {
					return true;			
			}
			
		}
		return false;
	}

	public List<Absence> findAllRequestedByEmployeeId(Long id) {
		return absenceRepository.findAllRequestedByEmployeeId(id);
	}

	public boolean acceptAbsence(AbsenceDTO absenceDTO) {
		
		Absence absence = findOne(absenceDTO.getId());
		
		Employee emp = employeeService.findOneByUsernameWithAppointments(absenceDTO.getEmployee().getUsername());
		LocalDate startDate2 = LocalDate.parse(absenceDTO.getTimePeriod().getStartDate());
		LocalDate endDate2 = LocalDate.parse(absenceDTO.getTimePeriod().getEndDate());
		LocalDate bookedAppointment = null;
		boolean isFree = true;
		for (Appointment appo : emp.getAppointments()) {
			if(appo.getStatus().equals(AppointmentStatus.RESERVED)) {
				LocalDateTime eWorkTSDateTime = appo.getTimePeriod().getStartDate().atTime(appo.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = appo.getTimePeriod().getEndDate().atTime(appo.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(endDate2.atTime(LocalTime.parse("00:00:00")))
						&& eWorkTSDateTime.isAfter(startDate2.atTime(LocalTime.parse("00:00:00"))))
						&& !(eWorkTEDateTime.isBefore(endDate2.atTime(LocalTime.parse("00:00:00")))
								&& eWorkTEDateTime.isBefore(startDate2.atTime(LocalTime.parse("00:00:00"))))) {
							isFree = false;
							bookedAppointment  = LocalDate.of(eWorkTSDateTime.getYear(), eWorkTEDateTime.getMonth(), eWorkTSDateTime.getDayOfMonth());
							break;				
				}
			}
		}
		
		if(isFree) {
			absence.setStatus(AbsenceStatus.APPROVED);
			emailService.sendEmailToEmployeeAboutAbsence(absence, "APPROVED", "");
		}
		else {
			absence.setStatus(AbsenceStatus.DENIED);
			emailService.sendEmailToEmployeeAboutAbsence(absence, "REJECTED", 
				"The reason for rejecting your "+ absence.getType() +" request is the booked appointment on " + bookedAppointment.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		}
		save(absence);
		
		return isFree;
	}
}
