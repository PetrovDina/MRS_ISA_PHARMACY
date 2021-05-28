package mrsisa12.pharmacy.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	
	public boolean checkEmployeeAbsences(TimePeriod tp, Employee emp) {
		List<Absence> absences = absenceRepository.findAllByEmployeeId(emp.getId());
		
		for (Absence absence : absences) {
			if(absence.getStatus()== AbsenceStatus.APPROVED) {
				LocalDateTime eWorkTSDateTime = absence.getTimePeriod().getStartDate().atTime(absence.getTimePeriod().getStartTime());
				LocalDateTime eWorkTEDateTime = absence.getTimePeriod().getEndDate().atTime(absence.getTimePeriod().getEndTime());
				if (!(eWorkTSDateTime.isAfter(tp.getEndDate().atTime(tp.getEndTime()))
						&& eWorkTSDateTime.isAfter(tp.getStartDate().atTime(tp.getStartTime())))
						&& !(eWorkTEDateTime.isBefore(tp.getEndDate().atTime(tp.getEndTime()))
								&& eWorkTEDateTime.isBefore(tp.getStartDate().atTime(tp.getStartTime())))) {
						return true;			
				}
			}
		}
		return false;
	}
}
