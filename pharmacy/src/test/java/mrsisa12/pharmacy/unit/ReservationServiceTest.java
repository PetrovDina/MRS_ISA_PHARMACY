package mrsisa12.pharmacy.unit;



import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.dto.ReservationDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.repository.ReservationRepository;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.ReservationService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
	
	@Mock
	private ReservationRepository reservationRepositoryMock;
	
	@Mock
	private Reservation reservationMock;
	

	@Mock
	private MedicationService medicationService;
	
	@InjectMocks
	private ReservationService reservationService;
	
	@InjectMocks
	@Spy
	private ReservationService reservationService2;
//	
//	@Test
//	@Transactional
//	@Rollback(true)
//	public void testCancelReservationInvalidiD() {
//		
//		Reservation reservation = reservationService.cancelReservation(999l, "patient");
//		assertNull(reservation);
//
//	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreateReservationValid() {
		ReservationDTO reservation = new ReservationDTO();
		
		PatientDTO patient = new PatientDTO();
		patient.setId(2l);
		MedicationDTO m = new MedicationDTO();
		m.setId(1l);
		PharmacyDTO p = new PharmacyDTO();
		p.setId(1l);
		
		reservation.setPatient(patient);
		reservation.setMedication(m);
		reservation.setPharmacy(p);
		reservation.setQuantity(1);
		reservation.setDueDate((new Date()));
		
		reservationService2.saveReservation(reservation);
        //assertThat(reservation.getPatient().getId()).isEqualTo(2l);

	}
}
