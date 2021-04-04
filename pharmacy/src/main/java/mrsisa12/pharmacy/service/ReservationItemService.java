package mrsisa12.pharmacy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.ReservationItem;
import mrsisa12.pharmacy.repository.ReservationItemRepository;

@Service
public class ReservationItemService {

	@Autowired
	private ReservationItemRepository reservationItemRepository;
	
	public ReservationItem findOne(Long id) {
		return reservationItemRepository.findById(id).orElseGet(null);
	}
	
	public List<ReservationItem> findAll() {
		return reservationItemRepository.findAll();
	}
	
	public Page<ReservationItem> findAll(Pageable page) {
		return reservationItemRepository.findAll(page);
	}
	
	public ReservationItem save(ReservationItem reservationItem) {
		return reservationItemRepository.save(reservationItem);
	}

	public void remove(Long id) {
		reservationItemRepository.deleteById(id);
	}
	
//	public ReservationItem findOneWithItemPrices(Long reservationItemId) {
//		return reservationItemRepository.findOneWithItemPrices(reservationItemId);
//	}
}
