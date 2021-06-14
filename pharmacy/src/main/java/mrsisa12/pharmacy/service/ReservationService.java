package mrsisa12.pharmacy.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.ReservationDTO;
import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.repository.ReservationRepository;
import mrsisa12.pharmacy.repository.TherapyRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private TherapyRepository therapyRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;

	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	private Random random = new Random();
    private static final String SOURCES ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
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
    
 
    @Transactional(propagation = Propagation.REQUIRED)
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
		List<Therapy> therapiesFromPharmacy = therapyRepository.findAllByPharmacyCompleted(pharmacy);
		String[] quarters = {"Q1", "Q2", "Q3", "Q4"};
		for (String quarter : quarters) {
			HashMap<String, Integer> dataFromQuarter = getAllMedicationConsumptedByQuarter(quarter, year, pharmacy, reservationsFromPharmacy, therapiesFromPharmacy);
			for(Map.Entry<String, Integer> entry : dataFromQuarter.entrySet()) {
				data.put(entry.getKey(), entry.getValue());
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByQuarter(String quarter, String year, Pharmacy pharmacy, List<Reservation> reservations, List<Therapy> therapies) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		if(reservations == null)
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		if(therapies == null)	
			therapies = therapyRepository.findAllByPharmacyCompleted(pharmacy);
		if(ReportDTO.quarters.containsKey(quarter)) {
			String[] monthsInQuarter = ReportDTO.quarters.get(quarter).split(",");
			for (String month : monthsInQuarter) {
				String monthTrimmed = month.trim();
				data.put(monthTrimmed, 0);
				HashMap<String, Integer> dataFromMonth = getAllMedicationConsumptedByMonthInYear(monthTrimmed, year, pharmacy, reservations, therapies);
				for(Map.Entry<String, Integer> entry : dataFromMonth.entrySet()) {
				    data.put(monthTrimmed, data.get(monthTrimmed) + entry.getValue());
				}
			}
		}
		return data;
	}

	public HashMap<String, Integer> getAllMedicationConsumptedByMonthInYear(String period, String year, Pharmacy pharmacy, List<Reservation> reservations, List<Therapy> therapies) {
		HashMap<String, Integer> data = new HashMap<String, Integer>();
		LocalDate startDate, endDate;
		
		String month = (ReportDTO.ReportMonths.valueOf(period).ordinal() < 9) ? "0"+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1) : ""+(ReportDTO.ReportMonths.valueOf(period).ordinal() + 1);
		LocalDate initial = LocalDate.parse(year+"-"+ month +"-01");
		startDate = initial.withDayOfMonth(1);
		endDate = initial.withDayOfMonth(initial.lengthOfMonth());
		if(reservations == null) 
			reservations = reservationRepository.findAllByPharmacyCompleted(pharmacy);
		if(therapies == null)	
			therapies = therapyRepository.findAllByPharmacyCompleted(pharmacy);
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
		    
		    for (Therapy therapy : therapies) {
				if(date.isEqual(therapy.getPrescribedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					for (TherapyItem therapyItem : therapy.getPrescriptionItems()) {
						data.put(dayInMonth, data.get(dayInMonth) + therapyItem.getQuantity());
					}
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
	
	
	
@Transactional(propagation = Propagation.REQUIRED)
public String saveReservation(ReservationDTO resDTO) throws IllegalArgumentException{
		
		Reservation reservation = new Reservation();
		
		Pharmacy pharmacy = pharmacyService.findOne(resDTO.getPharmacy().getId());
		Patient patient = patientService.findByUsername(resDTO.getPatient().getUsername());
		Medication medication = medicationService.findOne(resDTO.getMedication().getId());
		
		reservation.setPharmacy(pharmacy);
		reservation.setPatient(patient);
		reservation.setMedication(medication);
		reservation.setDueDate(resDTO.getDueDate());
		reservation.setQuantity(resDTO.getQuantity());
		reservation.setStatus(ReservationStatus.CREATED);
		
		
		//generates code
		int length = 10;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
		reservation.setCode(new String(text));
		

		
		//pesimisticni pristup metodi repozitorijuma - PESSIMISTIC WRITE
		PharmacyStorageItem psi = pharmacyStorageItemService.findOneWithMedicationAndPharmacy(medication.getId(), pharmacy.getId());
		
		//provera da li je broj na stanju manji od trazenog i baciti gresku ako jeste *dodato zbog konkurentnosti*
		
		if (psi.getQuantity() < resDTO.getQuantity()) {
			throw new IllegalArgumentException();
		}
		psi.setQuantity(psi.getQuantity()-resDTO.getQuantity());
		pharmacyStorageItemService.save(psi);

		//checking loyalty system price discount
		double price = resDTO.getMedicationPrice();
		double finalPrice = loyaltyProgramService.getFinalPrice(price, patient) * reservation.getQuantity();
		
		reservation.setMedicationPrice(loyaltyProgramService.getFinalPrice(price, patient));
		
		Integer pointsForPatient = medication.getLoyaltyPoints() * reservation.getQuantity();
		String message = loyaltyProgramService.generateReservationMessage(patient, finalPrice, pointsForPatient);
		patientService.addPointsAndUpdateCategory(patient, pointsForPatient);
		

		reservation = this.save(reservation);
		
		// email!
		String emailBody = "This email is confirmation that you have successfully reserved " + medication.getName() + ". Your unique reservation number is: " + reservation.getCode();
		EmailContent email = new EmailContent("Medicine reservation confirmation", emailBody);
		email.addRecipient(reservation.getPatient().getEmail());
        emailService.sendEmail(email);
		return message;
	}


@Transactional(propagation = Propagation.REQUIRED)
public Reservation cancelReservation(Long reservationId, String patientUsername) {	
	
	Reservation reservation = this.findOne(reservationId);

	if(reservation != null) {
		
		Patient patient = patientService.findByUsername(patientUsername);
		Integer pointsToLoose = reservation.getMedication().getLoyaltyPoints() * reservation.getQuantity();
		patientService.addPointsAndUpdateCategory(patient, (-pointsToLoose));
		
		//setting reservation status to cancelled
        reservation.setStatus(ReservationStatus.CANCELLED);
		
		//updating storage item quantity - pessimistic write!
		PharmacyStorageItem psi = pharmacyStorageItemService.findOneWithMedicationAndPharmacy(reservation.getMedication().getId(), reservation.getPharmacy().getId());
		psi.setQuantity(psi.getQuantity() + reservation.getQuantity());
		
		this.save(reservation);
		pharmacyStorageItemService.save(psi);

		
        return reservation;
    }
    else
        return null;
}

}
