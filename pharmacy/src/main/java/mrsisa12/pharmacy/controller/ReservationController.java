package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ReservationDTO;
import mrsisa12.pharmacy.dto.ReservationPickupDTO;
import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private LoyaltyProgramService loyaltyProgramService;
	
	private Random random = new Random();
    private static final String SOURCES ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	

	
	@GetMapping(value = "/findByPharmacy")
	public ResponseEntity<List<ReservationDTO>> getReservationsByPharmacy(@RequestParam Long pharmacyId) {
		List<Reservation> reservations = reservationService.findAllByPharmacy(pharmacyId);

		// convert reservations to DTOs
		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}
		return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/findByPatient")
	public ResponseEntity<List<ReservationDTO>> getReservationsByPatient(@RequestParam String username) {
		System.out.println(username);
		List<Reservation> reservations = reservationService.findAllByPatient(username);
		System.out.println(reservations.size());

		// convert reservations to DTOs
		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}
		return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@GetMapping(value = "/cancel")
	public ResponseEntity<ReservationDTO> cancelReservation(@RequestParam Long reservationId, @RequestParam String patientUsername) {
		
		
		Reservation reservation = reservationService.cancelReservation(reservationId, patientUsername);

		if(reservation != null) {
			

            return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	
	@PreAuthorize("hasRole('PATIENT')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<String> saveReservation(@RequestBody ReservationDTO resDTO) {
		
		try {
			String message = reservationService.saveReservation(resDTO);
			return new ResponseEntity<>(message, HttpStatus.CREATED);


		}catch(IllegalArgumentException e) {
			return new ResponseEntity<>("Unfortunately, we don't have enough medicine in storage. Please try again later.", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@GetMapping(value = "/pickup/{code}")
    public ResponseEntity<ReservationPickupDTO> getReservationForPickup(@PathVariable String code, @RequestParam Long pharmId){
		List<Reservation> reservations = reservationService.findAllByPharmacy(pharmId);
		
        Reservation reservation = null;
        for (Reservation res : reservations) {
			if(res.getCode().equals(code) ) {
				reservation = res;
				break;
			}
		}
        if(reservation != null) {
        	if(reservation.getDueDate().before(new Date(System.currentTimeMillis() + 3600 * 24000))) {
                reservation.setStatus(ReservationStatus.EXPIRED);
        		reservationService.update(reservation);}
            return new ResponseEntity<>(new ReservationPickupDTO(reservation), HttpStatus.OK);}
        else
            return new ResponseEntity<>(null, HttpStatus.OK);
    }
	
	@PreAuthorize("hasAnyRole('DERMATOLOGIST', 'PHARMACIST')")
	@PostMapping(value = "/confirm-pickup")
    public ResponseEntity<Boolean> confirmPickup(@RequestBody String id){
		String sub = id.substring(18, id.length()-2);
        try {
            boolean success = reservationService.confirmPickup(Long.parseLong(sub));
            if(success)
                return new ResponseEntity<>(success, HttpStatus.OK);
            else
                return ResponseEntity.notFound().build();
        } catch (ObjectOptimisticLockingFailureException e){
            return ResponseEntity.badRequest().build();
        }
    }
	
	@GetMapping(value = "/reportMedicationConsumptionYear")
	public ResponseEntity<ReportDTO> reportAppointmentYear(@RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = reservationService.getAllMedicationConsumptedByYear(year, pharmacyService.findOne(pharmacyId));
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportMedicationConsumptionQuarter")
	public ResponseEntity<ReportDTO> reportAppointmentQuarter(@RequestParam String quarter, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = reservationService.getAllMedicationConsumptedByQuarter(quarter, year, pharmacyService.findOne(pharmacyId), null, null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportMedicationConsumptionMonth")
	public ResponseEntity<ReportDTO> reportAppointmentMonth(@RequestParam String period, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = reservationService.getAllMedicationConsumptedByMonthInYear(period, year, pharmacyService.findOne(pharmacyId), null, null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<ReservationDTO>> getAllReservations() {

		List<Reservation> reservations = reservationService.findAll();

		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}

		return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<ReservationDTO>> getAllReservationsPage(Pageable page) {

		Page<Reservation> reservations = reservationService.findAll(page);

		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}

		return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ReservationDTO> getReservation(@PathVariable Long id) {

		Reservation reservation = reservationService.findOne(id);

		if (reservation == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.OK);
	}
	
}

