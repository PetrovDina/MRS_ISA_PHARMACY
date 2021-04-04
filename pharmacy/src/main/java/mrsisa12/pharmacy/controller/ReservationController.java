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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.PharmacyDTO;
import mrsisa12.pharmacy.dto.ReservationDTO;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.service.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {

	@Autowired
	private ReservationService reservationService;
	
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
