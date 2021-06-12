package mrsisa12.pharmacy.unit;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import mrsisa12.pharmacy.service.PatientService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
	
	@Mock
	private PatientRepository patientRepositoryMock;
	

	@InjectMocks
	private PatientService patientService;


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
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testEditPatientInfoValid() {
		
		PatientDTO p = new PatientDTO();
		p.setId(2l);
		p.setFirstName("Branislava");
		p.setLastName("Popov");
		p.setEmail("branislava@gmail.com");
		p.setGender(Gender.FEMALE);
		Location l = new Location();
		l.setCity("Zrenjanin");
		l.setStreet("Prvomajska");
		l.setStreetNum(2);
		l.setZipcode("23000");
		p.setLocation(l);
		
		Patient patient = patientService.updatePatient(p);
		assertEquals(patient.getFirstName(), "Branislava");

	}
}
