package mrsisa12.pharmacy.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.dto.OfferDTO;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Offer;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.SupplierStorageItem;
import mrsisa12.pharmacy.model.enums.MedicationForm;
import mrsisa12.pharmacy.model.enums.OfferStatus;
import mrsisa12.pharmacy.model.enums.OrderStatus;
import mrsisa12.pharmacy.repository.OfferRepository;
import mrsisa12.pharmacy.service.OfferService;
import mrsisa12.pharmacy.service.OrderService;
import mrsisa12.pharmacy.service.SupplierService;
import mrsisa12.pharmacy.service.SupplierStorageItemService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OfferServiceTest {
	
	@Mock
	private OrderService orderServiceMock;
	
	@Mock
	private SupplierService supplierServiceMock;
	
	@Mock
	private OfferRepository offerRepositoryMock;
	
	@Mock
	private SupplierStorageItemService supplierStorageItemServiceMock;
	
	@Mock
	private Offer offerMock;
	
	@InjectMocks
	private OfferService offerService;
	
	@Test
	public void testUpdateOffer() {
		Supplier supplier = new Supplier();
		supplier.setUsername("supplier");
		
		Order order = new Order();
		order.setId(1L);
		
		offerMock = new Offer(1L, supplier, order, 2000.0, LocalDate.now().minusDays(2), OfferStatus.PENDING);
		OfferDTO offerDTO = new OfferDTO(offerMock);
		offerDTO.setPrice(1900.0);
		offerDTO.setDeliveryDueDate(LocalDate.now().toString());
		
		when(offerRepositoryMock.findById(offerMock.getId())).thenReturn(Optional.of(offerMock));
		
		Offer offer = offerService.updateOffer(offerDTO);
	
		assertEquals(offer.getPrice(), 1900.0, 1);
		assertEquals(offer.getDeliveryDueDate(), LocalDate.now());
	}
	
	@Test
	public void testCreateOffer() {
		Supplier supplier = new Supplier();
		supplier.setUsername("supplier");
		
		Medication medication1 = new Medication(1l, "Lek1", "Proizvodjac1", false, MedicationForm.PILL);
		SupplierStorageItem s1 = new SupplierStorageItem(1L, 10, medication1, supplier, false);
		s1.setReservedQuantity(2);
		
		Medication medication2 = new Medication(2l, "Lek2", "Proizvodjac2", false, MedicationForm.PILL);
		SupplierStorageItem s2 = new SupplierStorageItem(2L, 5, medication2, supplier, false);
		s2.setReservedQuantity(3);
		
		Medication medication3 = new Medication(3l, "Lek3", "Proizvodjac3", false, MedicationForm.PILL);
		SupplierStorageItem s3 = new SupplierStorageItem(3L, 4, medication3, supplier, false);
		s3.setReservedQuantity(0);
		
		List<SupplierStorageItem> items = new ArrayList<SupplierStorageItem>();
		items.add(s1); items.add(s2); items.add(s3);
		supplier.setSupplierStorageItems(items);
		
		
		Order order = new Order();
		order.setId(1L);
		order.setStatus(OrderStatus.NEW);
		
		OrderItem o1 = new OrderItem(1l, 7, medication1, order, false);
		OrderItem o2 = new OrderItem(2l, 1, medication2, order, false);
		
		List<OrderItem> oItems = new ArrayList<OrderItem>();
		oItems.add(o1); oItems.add(o2);
		order.setOrderItems(oItems);
		

		offerMock = new Offer(1L, supplier, order, 2000.0, LocalDate.now(), OfferStatus.PENDING);
		OfferDTO offerDTO = new OfferDTO(offerMock);
		
		when(supplierServiceMock.findOneWithStorageItems(supplier.getUsername())).thenReturn(supplier);
		when(orderServiceMock.findOneWithOrderItems(order.getId())).thenReturn(order);
		
		Offer offer = offerService.createOffer(offerDTO);
	
		assertEquals(offer.getOrder().getOrderItems().size(), 2);
		assertEquals(offer.getOrder().getStatus(), OrderStatus.HAS_OFFERS);
		assertEquals(offer.getPrice(), 2000.0, 1);
		assertEquals(offer.getDeliveryDueDate(), LocalDate.now());
		verify(supplierStorageItemServiceMock, times(2)).
				updateSupplierStorageItemReservedQuantity(Mockito.any(Medication.class), Mockito.any(Supplier.class), Mockito.anyInt());
	}
	
	@Test
	public void testCreateOfferFailed() {
		Supplier supplier = new Supplier();
		supplier.setUsername("supplier");
		
		Medication medication1 = new Medication(1l, "Lek1", "Proizvodjac1", false, MedicationForm.PILL);
		SupplierStorageItem s1 = new SupplierStorageItem(1L, 10, medication1, supplier, false);
		s1.setReservedQuantity(2);
		
		Medication medication2 = new Medication(2l, "Lek2", "Proizvodjac2", false, MedicationForm.PILL);
		SupplierStorageItem s2 = new SupplierStorageItem(2L, 5, medication2, supplier, false);
		s2.setReservedQuantity(3);
		
		Medication medication3 = new Medication(3l, "Lek3", "Proizvodjac3", false, MedicationForm.PILL);
		SupplierStorageItem s3 = new SupplierStorageItem(3L, 4, medication3, supplier, false);
		s3.setReservedQuantity(0);
		
		List<SupplierStorageItem> items = new ArrayList<SupplierStorageItem>();
		items.add(s1); items.add(s2); items.add(s3);
		supplier.setSupplierStorageItems(items);
		
		
		Order order = new Order();
		order.setId(1L);
		
		OrderItem o1 = new OrderItem(1l, 71, medication1, order, false);
		OrderItem o2 = new OrderItem(2l, 1, medication2, order, false);
		
		List<OrderItem> oItems = new ArrayList<OrderItem>();
		oItems.add(o1); oItems.add(o2);
		order.setOrderItems(oItems);
		

		offerMock = new Offer(1L, supplier, order, 2000.0, LocalDate.now(), OfferStatus.PENDING);
		OfferDTO offerDTO = new OfferDTO(offerMock);
		
		when(supplierServiceMock.findOneWithStorageItems(supplier.getUsername())).thenReturn(supplier);
		when(orderServiceMock.findOneWithOrderItems(order.getId())).thenReturn(order);
		
		Offer offer = offerService.createOffer(offerDTO);
	
		assertEquals(null, offer);
	}
	
	
	

}
