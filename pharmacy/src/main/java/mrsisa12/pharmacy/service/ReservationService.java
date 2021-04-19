package mrsisa12.pharmacy.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
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

	public List<Reservation> findAllByPharmacy(Long pharmacyId) {
		return reservationRepository.findAllByPharmacy(pharmacyId);
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

            //TODO email
            return true;

        }
        return false;
    }
	
//	public List<Reservation> findAllByName(String name) {
//		return reservationRepository.findAllByName(name);
//	}
//	
//	public List<Pharmacy> findByNameAllIgnoringCase(String name) {
//		return reservationRepository.findByNameAllIgnoringCase(name);
//	}
//	
//	public Reservation findOneWithReservationItems(Long reservationId) {
//		return reservationRepository.findOneWithReservationItems(reservationId);
//	}
}
