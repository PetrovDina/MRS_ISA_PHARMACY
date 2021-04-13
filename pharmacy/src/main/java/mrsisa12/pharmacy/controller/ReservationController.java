package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.ReservationDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
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
	
	@GetMapping(value = "/findByPharmacy")
	public ResponseEntity<List<ReservationDTO>> getReservationsByPharmacy(@RequestParam Long pharmacyId) {
		System.out.println(pharmacyId);
		List<Reservation> reservations = reservationService.findAllByPharmacy(pharmacyId);
		System.out.println(reservations.size());

		// convert reservations to DTOs
		List<ReservationDTO> reservationsDTO = new ArrayList<>();
		for (Reservation r : reservations) {
			reservationsDTO.add(new ReservationDTO(r));
		}
		return new ResponseEntity<>(reservationsDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO resDTO) {
		
		System.out.println(resDTO.toString());
		Reservation reservation = new Reservation();
		
		Pharmacy pharmacy = pharmacyService.findOne(resDTO.getPharmacy().getId());
		Patient patient = patientService.findOne(resDTO.getPatient().getId());
		Medication medication = medicationService.findOne(resDTO.getMedication().getId());
		
		reservation.setPharmacy(pharmacy);
		reservation.setPatient(patient);
		reservation.setMedication(medication);
		reservation.setDueDate(resDTO.getDueDate());
		reservation.setQuantity(resDTO.getQuantity());
		reservation.setStatus(ReservationStatus.CREATED);


		reservation = reservationService.save(reservation);
		return new ResponseEntity<>(new ReservationDTO(reservation), HttpStatus.CREATED);
	}
}
