package mrsisa12.pharmacy.unit;



import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.repository.AppointmentRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {
	
	@Mock
	public AppointmentRepository appointmentRepositoryMock;
}
