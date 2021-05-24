package mrsisa12.pharmacy.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
	
	public Page<Reservation> findAll(Pageable pageable);

	
	@Query("select res from Reservation res where res.pharmacy.id = ?1")
	public List<Reservation> findAllByPharmacy(Long pharmacyId);
	
	@Query("select res from Reservation res where res.patient.username = ?1")
	public List<Reservation> findAllByPatient(String patientUserName);

	@Query("select res from Reservation res where res.status = 'CREATED'")
	public List<Reservation> findAllCreated();
	
	@Query("select res from Reservation res where res.status = 'COMPLETED' and res.pharmacy = ?1")
	public List<Reservation> findAllByPharmacyCompleted(Pharmacy pharmacy);
	
//	public List<Reservation> findAllByName(String name);
//	
//	public List<Reservation> findByNameAllIgnoringCase(String name);
//	
	//todo proveri da li je okej
//	@Query("select r from Reservation r join fetch r.reservationItems e where r.id =?1")
//	public Reservation findOneWithReservationItems(Long reservationId);

}
