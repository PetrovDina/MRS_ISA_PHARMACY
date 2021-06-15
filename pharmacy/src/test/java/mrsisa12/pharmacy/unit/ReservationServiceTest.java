package mrsisa12.pharmacy.unit;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.mail.EmailContent;
import mrsisa12.pharmacy.mail.EmailService;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.repository.ReservationRepository;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.ReservationService;
import mrsisa12.pharmacy.service.TherapyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationServiceTest {
	
	@Mock
	private TherapyItem therapyItemMock;
	
	@Mock
	private PharmacyStorageItemService pharmacyStorageItemServiceMock;
	
	@Mock
	private TherapyService therapyServiceMock;
	
	@Mock
	private LoyaltyProgramService loyaltyProgramServiceMock;
	
	@Mock
	private PatientService patientServiceMock;
	
	@Mock
	private ReservationRepository reservationRepositoryMock;
	
	@Mock
	private EmailService emailServiceMock;
		
	@InjectMocks
	private ReservationService reservationServiceMock;
	
	@Test
	@Transactional
	@Rollback(true)
    public void testConfirmPickupCorrect(){
		Pharmacy pharmacyMock = new Pharmacy();
    	pharmacyMock.setName("Benu");
    	pharmacyMock.setId(1l);
    	pharmacyMock.setRating(4.0);
    	Location l1 = new Location();
		l1.setCity("Zrenjanin");
		l1.setStreet("Prvomajska");
		l1.setStreetNum(3);
		l1.setZipcode("23000");
		pharmacyMock.setLocation(l1);
    	
    	Patient patientMock = new Patient();
		patientMock.setId(1l);
		patientMock.setUsername("patient");
		patientMock.setFirstName("Branislava");
		patientMock.setLastName("Popov");
		patientMock.setEmail("branislava@gmail.com");
		patientMock.setGender(Gender.FEMALE);
		Location l = new Location();
		l.setCity("Zrenjanin");
		l.setStreet("Prvomajska");
		l.setStreetNum(2);
		l.setZipcode("23000");
		patientMock.setLocation(l);
		
		Medication medicationMock = new Medication();
		medicationMock.setId(1l);
		medicationMock.setName("Probiotik");
		medicationMock.setRating(4.0);
		medicationMock.setLoyaltyPoints(5);
		
		Reservation resMock = new Reservation();
		resMock.setId(2l);
		resMock.setCode("1234567890");
		String string = "1.25.2121."; 
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy.", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		resMock.setDueDate(date);
		resMock.setPatient(patientMock);
		resMock.setStatus(ReservationStatus.CREATED);
		resMock.setQuantity(5);
		resMock.setPharmacy(pharmacyMock);
		resMock.setMedication(medicationMock);
		resMock.setMedicationPrice(500);
		
		String emailBody = "This email is confirmation that you have successfully picked up order #";
		EmailContent emailMock = new EmailContent("Medicine pickup confirmation!",emailBody + resMock.getCode() + "!");
		emailMock.addRecipient(resMock.getPatient().getEmail());
		
		when(reservationRepositoryMock.findByReservationId(resMock.getId())).thenReturn(resMock);
		//when(emailServiceMock.sendEmail(emailMock)).thenReturn(null);
		when(reservationRepositoryMock.save(resMock)).thenReturn(resMock);		
		
		boolean result = reservationServiceMock.confirmPickup(String.valueOf(resMock.getId()));
		assertTrue(result);
		
    }
	
	@Test
	@Transactional
	@Rollback(true)
    public void testConfirmPickupIncorrect(){
		Pharmacy pharmacyMock = new Pharmacy();
    	pharmacyMock.setName("Benu");
    	pharmacyMock.setId(1l);
    	pharmacyMock.setRating(4.0);
    	Location l1 = new Location();
		l1.setCity("Zrenjanin");
		l1.setStreet("Prvomajska");
		l1.setStreetNum(3);
		l1.setZipcode("23000");
		pharmacyMock.setLocation(l1);
    	
    	Patient patientMock = new Patient();
		patientMock.setId(1l);
		patientMock.setUsername("patient");
		patientMock.setFirstName("Branislava");
		patientMock.setLastName("Popov");
		patientMock.setEmail("branislava@gmail.com");
		patientMock.setGender(Gender.FEMALE);
		Location l = new Location();
		l.setCity("Zrenjanin");
		l.setStreet("Prvomajska");
		l.setStreetNum(2);
		l.setZipcode("23000");
		patientMock.setLocation(l);
		
		Medication medicationMock = new Medication();
		medicationMock.setId(1l);
		medicationMock.setName("Probiotik");
		medicationMock.setRating(4.0);
		medicationMock.setLoyaltyPoints(5);
		
		Reservation resMock = new Reservation();
		resMock.setId(2l);
		resMock.setCode("1234567890");
		String string = "1.25.2121."; 
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy.", Locale.ENGLISH);
		Date date = null;
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		resMock.setDueDate(date);
		resMock.setPatient(patientMock);
		resMock.setStatus(ReservationStatus.COMPLETED);
		resMock.setQuantity(5);
		resMock.setPharmacy(pharmacyMock);
		resMock.setMedication(medicationMock);
		resMock.setMedicationPrice(500);
		
		String emailBody = "This email is confirmation that you have successfully picked up order #";
		EmailContent emailMock = new EmailContent("Medicine pickup confirmation!",emailBody + resMock.getCode() + "!");
		emailMock.addRecipient(resMock.getPatient().getEmail());
		
		when(reservationRepositoryMock.findByReservationId(resMock.getId())).thenReturn(resMock);
		//when(emailServiceMock.sendEmail(emailMock)).thenReturn(null);
		when(reservationRepositoryMock.save(resMock)).thenReturn(resMock);		
		
		boolean result = reservationServiceMock.confirmPickup(String.valueOf(resMock.getId()));
		assertTrue(!result);
		
    }

}
