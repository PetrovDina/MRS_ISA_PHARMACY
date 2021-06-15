package mrsisa12.pharmacy.unit;



import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.MedicationRating;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.repository.MedicationRepository;
import mrsisa12.pharmacy.service.MedicationRatingService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PatientService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationServiceTest {
	
	@Mock
	private MedicationRepository medicationRepositoryMock;
	
	@Mock
	private PatientService patientServiceMock;
	
	@Mock
	private MedicationRatingService medicationRatingServiceMock;
	
	@Mock
	private Medication medicationMock;

	@InjectMocks
	private MedicationService medicationService;

	
	@Test
	@Transactional
	@Rollback(true)
	public void testRateNewMedicationValid() {
		
		Patient patientMock = new Patient();
		patientMock.setId(2l);
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
		
		MedicationRating rating1 = new MedicationRating();
		rating1.setMedication(medicationMock);
		rating1.setRating(5.0);
		
		MedicationRating rating2 = new MedicationRating();
		rating2.setMedication(medicationMock);
		rating2.setRating(3.0);
		
		when(patientServiceMock.findByUsername(patientMock.getUsername())).thenReturn(patientMock);
		when(medicationRepositoryMock.findById(medicationMock.getId())).thenReturn(Optional.of(medicationMock));
		when(medicationRatingServiceMock.findOneByPatientAndMedication(patientMock.getUsername(), medicationMock.getId())).thenReturn(null);
		when(medicationRatingServiceMock.findAllByMedication(medicationMock.getId())).thenReturn(Arrays.asList(rating1, rating2));
		
		
		double result = medicationService.rateMedication(patientMock.getUsername(), medicationMock.getId(), 5.0);
		assertEquals(result, 4.5, 1);
		

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangeRatingMedicationValid() {
		
		Patient patientMock = new Patient();
		patientMock.setId(2l);
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
		
		
		MedicationRating rating1 = new MedicationRating();
		rating1.setMedication(medicationMock);
		rating1.setRating(5.0);

		
		MedicationRating rating2 = new MedicationRating(); //patient's old rating
		rating2.setMedication(medicationMock);
		rating2.setRating(3.0);
		rating2.setPatient(patientMock);
		
		when(patientServiceMock.findByUsername(patientMock.getUsername())).thenReturn(patientMock);
		when(medicationRepositoryMock.findById(medicationMock.getId())).thenReturn(Optional.of(medicationMock));
		when(medicationRatingServiceMock.findOneByPatientAndMedication(patientMock.getUsername(), medicationMock.getId())).thenReturn(rating2);
		when(medicationRatingServiceMock.findAllByMedication(medicationMock.getId())).thenReturn(Arrays.asList(rating1, rating2));
		
		
		double result = medicationService.rateMedication(patientMock.getUsername(), medicationMock.getId(), 1.0);
		assertEquals(result, 3.0, 1);
		


	}
	
	
	@Test(expected=IllegalArgumentException.class)
	@Transactional
	@Rollback(true)
	public void testChangeRatingMedicationInvalidNumber() {
		
		Patient patientMock = new Patient();
		patientMock.setId(2l);
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
		

		medicationService.rateMedication(patientMock.getUsername(), medicationMock.getId(), -1.0);
		


	}
	

}
