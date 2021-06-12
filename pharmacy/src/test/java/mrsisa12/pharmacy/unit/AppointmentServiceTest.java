package mrsisa12.pharmacy.unit;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacist;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.AppointmentType;
import mrsisa12.pharmacy.repository.AppointmentRepository;
import mrsisa12.pharmacy.service.AppointmentService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AppointmentServiceTest {
	
	@Mock
	private AppointmentRepository appointmentRepositoryMock;
	
//	@Mock
//	private EmployeeService employeeServiceMock;
//	
//	@Mock
//	private PharmacyService pharmacyServiceMock;
//	
//	@Mock
//	private PatientService patientServiceMock;
//	
//	@Mock
//	private Appointment appointmentMock;
//	
//	@Mock
//	private LoyaltyProgramService loyaltyProgramServiceMock;
//	
	@InjectMocks
	private AppointmentService appointmentService;
	
//	@Test
//	public void testBookPharmacistAppointmentValid() {
//		/*
//		Kako za testove koristimo mokovane repository objekte moramo da definišemo šta će se desiti kada se
//		pozove određena metoda kombinacijom "when"-"then" Mockito metoda.
//		 */
//		Long employeeId = 1L;
//		
//		TimePeriod tp = new TimePeriod(LocalDate.now(), LocalTime.now(), LocalDate.now(), LocalTime.now().plusHours(1));
//		Pharmacy pharmacy = new Pharmacy();
//		pharmacy.setId(1L);
//		pharmacy.setLocation(new Location());
//		pharmacy.setAppointmentPriceCatalog(new AppointmentPriceCatalog(2000, 1000));
//		
//		
//		Pharmacist pharmacist = new Pharmacist();
//		pharmacist.setId(1L);
//		
//		
//		Appointment appointment = new Appointment(AppointmentStatus.RESERVED, tp, pharmacist, new Patient(), 1000, pharmacy, AppointmentType.PHARMACIST_CONSULTATION);
//		
//		pharmacist.addAppointment(appointment);
//		
//		Patient patient = new Patient();
//		patient.setLoyaltyPoints(10);
//		patient.setCategory(PatientCategory.REGULAR);
//		
//		LoyaltyProgram lp = new LoyaltyProgram(1L, 10, 100, 300, 10, 30);
//		
//		String stringic = "Appointment booked.";
//		
//		// za akciju
//		AppointmentDTO appointmentDTO = new AppointmentDTO(appointment);
//		appointmentDTO.getTimePeriod().setStartTime(appointment.getTimePeriod().getStartTime().plusHours(3).toString());
//		appointmentDTO.getTimePeriod().setEndTime(appointment.getTimePeriod().getEndTime().plusHours(3).toString());
//		
//		// 1. Definisanje ponašanja mock objekata
//		when(employeeServiceMock.findOneEmployee(employeeId)).thenReturn(pharmacist);
//		when(employeeServiceMock.findOneWithAllAppointments(employeeId)).thenReturn(pharmacist);
//		when(patientServiceMock.findByUsername("patient")).thenReturn(patient);
//		when(pharmacyServiceMock.findOne(1L)).thenReturn(pharmacy);
//		when(loyaltyProgramServiceMock.getFinalAppointmentPrice(Mockito.any(Double.class), Mockito.any(Patient.class))).thenReturn(1000.0);
//		when(loyaltyProgramServiceMock.appointmentPoints()).thenReturn(10);
//		when(loyaltyProgramServiceMock.generateAppointmentMessage(Mockito.any(Patient.class), Mockito.any(Double.class), Mockito.any(Integer.class))).thenReturn(stringic);
//		when(appointmentRepositoryMock.save(Mockito.any(Appointment.class))).thenReturn(appointment);
//		
//		// 2. Akcija
//		String message = appointmentService.bookPharmacistAppointment(appointmentDTO);
//		
//		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
//		assertEquals(message, stringic);
//		
//		/*
//		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
//		 */
////		verify(studentRepositoryMock, times(1)).findAll();
////        verifyNoMoreInteractions(studentRepositoryMock);
//	}
	@Test
	public void testFindAllConcludedByPatientAndPharmacy() {
		
		String patientUsername = "patient";
		Long pharmacyId = 1L;
		
		List<Appointment> appointments = new ArrayList<Appointment>();
		appointments.add(new Appointment(AppointmentStatus.CONCLUDED, new TimePeriod(), new Pharmacist(), new Patient(), 1000.0, new Pharmacy(),AppointmentType.PHARMACIST_CONSULTATION));
		
		// 1. Definisanje ponašanja mock objekata
		Mockito.when(appointmentRepositoryMock.findAllConcludedByPatientAndPharmacy(patientUsername, pharmacyId)).thenReturn(appointments);
	
		// 2. Akcija
		List<Appointment> appointmentsConcluded = appointmentService.findAllConcludedByPatientAndPharmacy(patientUsername, pharmacyId);
		
		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
		assertThat(appointmentsConcluded).hasSize(1);
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
	}
}
