package mrsisa12.pharmacy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.ReservationService;

@Controller
public class SchedulerController {
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	PatientService patientService;
	
	@Autowired
	AppointmentService appointmentService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * Logika se izvrsava u vremenskim trenucima definisanih cron sintaksom. Vise o cron sintaksi mozete procitati na linku:
	 * http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/scheduling/support/CronSequenceGenerator.html
	 * 
	 * DINA:
	 * https://crontab.guru/ - Sajt gde lako mozete da postavite sintaksu koju ocete. Vodite racuna da na tom sajtu ima 5 polja a ne 6 -> fale im sekunde!
	 */
	
	
	//u 2 ujutru svakog dana - At 02:00 every single day
	@Scheduled(cron = "0 0 2 * * *")
	public void penaliseUncompletedReservations() {
		logger.info("> SYSTEM CLOCK: - penalising uncompleted reservations");
		reservationService.penaliseUncompletedReservations();
		logger.info("< SYSTEM CLOCK: - finished penalising uncompleted reservations");
	}
	
	//u 2 ujutru svakog prvog u mesecu - At 02:00 on day-of-month 1.
	@Scheduled(cron = "0 0 2 1 * *")
	public void resetPenals() {
		
		try {
			logger.info("> SYSTEM CLOCK: - resetting patients' penals");
			patientService.resetPenals();
			logger.info("< SYSTEM CLOCK: - finished resetting patients' penals");
		}catch (ObjectOptimisticLockingFailureException e){
			return;
        }
		
	}
	
	//u 2 ujutru svakog dana - At 02:00 every single day
	@Scheduled(cron = "0 0 2 * * *")
	public void checkExpiredAppointments() {
		try {
			logger.info("> SYSTEM CLOCK: - checking expired appointments");
			appointmentService.checkExpiredAppointments();
			logger.info("< SYSTEM CLOCK: - finished checking expired appointments");
		}catch (ObjectOptimisticLockingFailureException e){
			return;
        }
		
	}

	

}