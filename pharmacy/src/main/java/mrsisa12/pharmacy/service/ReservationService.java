package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.repository.ReservationRepository;

@Service
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
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
	
//	public List<Reservation> findAllByName(String name) {
//		return reservationRepository.findAllByName(name);
//	}
//	
//	public List<Pharmacy> findByNameAllIgnoringCase(String name) {
//		return reservationRepository.findByNameAllIgnoringCase(name);
//	}
//	
	public Reservation findOneWithReservationItems(Long reservationId) {
		return reservationRepository.findOneWithReservationItems(reservationId);
	}
}
