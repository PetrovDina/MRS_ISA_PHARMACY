package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.ZoneId;
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

import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PatientService patientService;
	
	public Reservation findOne(Long id) {
		return reservationRepository.findById(id).orElseGet(null);
	}
	
	public List<Reservation> findAll() {
		return reservationRepository.findAll();
	}
	
	public Page<Reservation> findAll(Pageable page) {
		return reservationRepository.findAll(page);
	}
	
	public Reservation save(Reservation reservation) {
		return reservationRepository.save(reservation);
	}

	public void remove(Long id) {
		reservationRepository.deleteById(id);
	}

	public List<Reservation> findAllByPharmacy(Long pharmacyId) {
		return reservationRepository.findAllByPharmacy(pharmacyId);
	}
	
	public List<Reservation> findAllByPatient(String patientUsername) {
		return reservationRepository.findAllByPatient(patientUsername);
	}
	
	public Reservation update(Reservation reservation) {
        Reservation updatedReservation = reservationRepository.findById(reservation.getId()).orElseGet(null);
        updatedReservation.update(reservation);
        updatedReservation = reservationRepository.save(updatedReservation);

        return updatedReservation;
    }
	
    public boolean confirmPickup(Long id)  {
        String emailBody = "This email is confirmation that you have successfully picked up order #";
        Reservation toUpdate =  findOne(id);
        if(toUpdate != null && !toUpdate.getStatus().equals(ReservationStatus.COMPLETED) &&
                !(toUpdate.getDueDate().before(new Date()) && toUpdate.getDueDate().after(new Date(System.currentTimeMillis() - 3600 * 24000)))) {

            toUpdate.setStatus(ReservationStatus.COMPLETED);
            update(toUpdate);
            EmailContent email = new EmailContent("Medicine pickup confirmation!",emailBody + toUpdate.getId() + "!");
            email.addRecipient(toUpdate.getPatient().getEmail());
            emailService.sendEmail(email);
            return true;

        }
        return false;
    }
    
 
	@EventListener(ApplicationReadyEvent.class)
	public void penaliseUncompletedReservations() {
		List<Reservation> reservations = reservationRepository.findAllCreated();
		System.out.println("--GIVING PENALS FOR UNCOMPLETED RESERVATIONS--");

		for (Reservation reservation: reservations) {
			
			//reservation is not picked up and the due date is over => change status and give penalty point
			if (reservation.getDueDate().before(new Date())) {
				Patient patient = reservation.getPatient();
				reservation.setStatus(ReservationStatus.EXPIRED);
				patient.setPenaltyPoints(patient.getPenaltyPoints() + 1);
				
				reservationRepository.save(reservation);
				patientService.save(patient);
			}
		}
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByYear(String year, Pharmacy pharmacy) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		List<Reservation> reservationsFromPharmacy = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
		for (String quarter : quarters) {
			HashMap<String, Integer> dataFromQuarter = getAllMedicationConsumptedByQuarter(quarter, year, pharmacy, reservationsFromPharmacy);
			for(Map.Entry<String, Integer> entry : dataFromQuarter.entrySet()) {
				data.put(entry.getKey(), entry.getValue());
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByQuarter(String quarter, String year, Pharmacy pharmacy, List<Reservation> reservations) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		if(reservations == null)
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		if(ReportDTO.quarters.containsKey(quarter)) {
			String[] monthsInQuarter = ReportDTO.quarters.get(quarter).split(",");
			for (String month : monthsInQuarter) {
				String monthTrimmed = month.trim();
				data.put(monthTrimmed, 0);
				HashMap<String, Integer> dataFromMonth = getAllMedicationConsumptedByMonthInYear(monthTrimmed, year, pharmacy, reservations);
				for(Map.Entry<String, Integer> entry : dataFromMonth.entrySet()) {
				    data.put(monthTrimmed, data.get(monthTrimmed) + entry.getValue());
				}
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByMonthInYear(String period, String year, Pharmacy pharmacy, List<Reservation> reservations) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		LocalDate startDate, endDate;
		
		String month = (ReportDTO.ReportMonths.valueOf(period).ordinal() < 9) ? "0"+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1) : ""+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1);
		LocalDate initial = LocalDate.parse(year+"-"+ month +"-01");
		startDate = initial.withDayOfMonth(1);
		endDate = initial.withDayOfMonth(initial.lengthOfMonth());
		if(reservations == null) 
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		
		Calendar start = Calendar.getInstance();
		start.setTime(Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
		Calendar end = Calendar.getInstance();
		end.setTime(Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));

		for (LocalDate date = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); !start.after(end); start.add(Calendar.DATE, 1), date = start.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) {
		    String dayInMonth = String.valueOf(date.getDayOfMonth());
		    data.put(dayInMonth, 0);
		    for (Reservation reservation : reservations) {
				if(date.isEqual(reservation.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					data.put(dayInMonth, data.get(dayInMonth) + reservation.getQuantity());
				}
			}
		}
		return data;
	}

	public List<Reservation> findAllCompletedByPatientAndPharmacy(String patientUsername, Long pharmacyId) {
		return reservationRepository.findAllCompletedByPatientAndPharmacy(patientUsername, pharmacyId);
	}
	
	public List<Reservation> findAllCompletedByPatientAndMedication(String patientUsername, Long medicationId) {
		return reservationRepository.findAllCompletedByPatientAndMedication(patientUsername, medicationId);
	}

}
