package mrsisa12.pharmacy.unit;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.repository.PatientRepository;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.PatientService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
	
	@Mock
	private PatientRepository patientRepositoryMock;
	
	@Mock
	private LocationService locationServiceMock;
	
	@Mock
	private Patient patientMock;

	@InjectMocks
	private PatientService patientService;

	
	@Test
	@Transactional
	@Rollback(true)
	public void testEditPatientInfoValid() {
		
		patientMock = new Patient();
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
		
		PatientDTO patientDTO = new PatientDTO(patientMock);
		patientDTO.setId(patientMock.getId());
		
		when(patientRepositoryMock.findById(patientMock.getId())).thenReturn(Optional.of((patientMock)));

		patientMock = patientService.updatePatient(patientDTO);
		assertEquals(patientMock.getFirstName(), "Branislava");
		assertEquals(patientMock.getLastName(), "Popov");
		assertEquals(patientMock.getLocation().getCity(), "Zrenjanin");

	}
	


	@Test
	public void testFindAll() {
		Patient p = new Patient();
		p.setId(2l);
		p.setFirstName("Branislava");
		p.setLastName("Popov");
		p.setEmail("branislava@gmail.com");
		p.setGender(Gender.FEMALE);
		
		
		when(patientRepositoryMock.findAll()).thenReturn(Arrays.asList(p));
		
		List<Patient> patients = patientService.findAll();
		
		assertThat(patients).hasSize(1);


	}
	
	

}
