package mrsisa12.pharmacy.unit;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.dto.order.OrderWithOrderItemsDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.MedicationForm;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.repository.OrderRepository;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.OrderItemService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.PharmacyAdminService;
import mrsisa12.pharmacy.service.PharmacyService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
	
	@Mock
	private OrderRepository orderRepositoryMock;
	
//	@Mock
//	private EmployeeService employeeServiceMock;
//	
	@Mock
	private PharmacyService pharmacyServiceMock;
	
	@Mock
	private PharmacyAdminService pharmacyAdminServiceMock;
	
	@Mock
	private MedicationService medicationServiceMock;

	@Mock
	private OrderItemService orderItemServiceMock;
	
	@Mock
	private Order order;
	
	@InjectMocks
	private OrderService orderService;
	
	@Test
	public void testCreateOrder() {
		
		String padminUsername = "padmin";
		Long pharmacyId = 1L;
	
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1L);
		
		PharmacyAdmin pharmacyAdmin = new PharmacyAdmin();
		pharmacyAdmin.setId(1L);
		pharmacyAdmin.setUsername(padminUsername);
		pharmacyAdmin.setPharmacy(pharmacy);
		pharmacyAdmin.setPassword("padmin"); 
		pharmacyAdmin.setEmail("padmin@email.com");
		pharmacyAdmin.setFirstName("Padmin"); 
		pharmacyAdmin.setLastName("Padminic"); 
		pharmacyAdmin.setLocation(new Location());
		pharmacyAdmin.setGender(Gender.MALE);
		
		Order orderMocked = new Order();
		orderMocked.setDueDate(LocalDate.now());
		orderMocked.setId(1L);
		orderMocked.setPharmacyAdmin(pharmacyAdmin);
		orderMocked.setPharmacy(pharmacy);
		orderMocked.setStatus(OrderStatus.NEW);
		
		Medication medication = new Medication();
		medication.setId(1L);
		medication.setForm(MedicationForm.CAPSULE);
		
		OrderItem oi1 = new OrderItem();
		oi1.setMedication(medication);
		oi1.setOrder(orderMocked);
		oi1.setQuantity(10);
		
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		orderItems.add(oi1);
		
		orderMocked.setOrderItems(orderItems);
		
		OrderWithOrderItemsDTO orderDTO = new OrderWithOrderItemsDTO(orderMocked);
		
		// 1. Definisanje ponašanja mock objekata
		Mockito.when(pharmacyAdminServiceMock.findOneByUsername(padminUsername)).thenReturn(pharmacyAdmin);
		Mockito.when(pharmacyServiceMock.findOne(pharmacyId)).thenReturn(pharmacy);
		Mockito.when(orderRepositoryMock.save(Mockito.any(Order.class))).thenReturn(orderMocked);
		
		// 2. Akcija
		Order order = orderService.createOrder(orderDTO);
		
		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
		assertEquals(order.getDueDate(), orderMocked.getDueDate());
		assertEquals(order.getStatus(), OrderStatus.NEW);
		assertThat(order.getOrderItems()).hasSize(1);
		
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
	}
}
