package mrsisa12.pharmacy.unit;



import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.MedicationForm;
import mrsisa12.pharmacy.repository.PharmacyStorageItemRepository;
import mrsisa12.pharmacy.service.ItemPriceService;
import mrsisa12.pharmacy.service.MedicationService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyStorageItemServiceTest {
	
	@Mock
	private PharmacyStorageItemRepository pharmacyStorageItemRepositoryMock;
	
	@Mock
	private PharmacyService pharmacyServiceMock;
	
	@Mock
	private MedicationService medicationServiceMock;
	
	@Mock
	private ItemPriceService itemPriceServiceMock;
	
	@Mock
	private PharmacyStorageItem pharmacyStorageItem;
	
	@InjectMocks
	private PharmacyStorageItemService pharmacyStorageItemService;
	
	@Test
	public void testCreatePharmacyStorageItem() {
		
		Long pharmacyId = 1L;
		Long medicationId = 1L;
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(pharmacyId);
		pharmacy.setLocation(new Location());
		
		Medication medication = new Medication();
		medication.setId(1L);
		medication.setForm(MedicationForm.CAPSULE);
		
		PharmacyStorageItem psiMocked = new PharmacyStorageItem();
		psiMocked.setMedication(medication);
		psiMocked.setPharmacy(pharmacy);
		psiMocked.setQuantity(10);
		
		List<ItemPrice> itemPrices = new ArrayList<ItemPrice>();
		itemPrices.add(new ItemPrice(500, true, false, new TimePeriod()));
		psiMocked.setItemPrices(itemPrices);
		
		
		PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO = new PharmacyStorageItemWithItemPricesDTO(psiMocked);
		
		// 1. Definisanje ponašanja mock objekata
		Mockito.when(pharmacyServiceMock.findOne(pharmacyId)).thenReturn(pharmacy);
		Mockito.when(medicationServiceMock.findOne(medicationId)).thenReturn(medication);
		Mockito.when(pharmacyStorageItemRepositoryMock.save(Mockito.any(PharmacyStorageItem.class))).thenReturn(psiMocked);
		
		// 2. Akcija
		PharmacyStorageItem psi = pharmacyStorageItemService.createPharmacyStorageItem(pharmacyStorageItemWIPDTO);
		
		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
		assertEquals(psi.getQuantity(), psiMocked.getQuantity());
		assertEquals(psi.getCounter(), 0);
		assertEquals(psi.getMedication(), psiMocked.getMedication());
		assertEquals(psi.getPharmacy(), psiMocked.getPharmacy());
		
		/*
		Možemo verifikovati ponašanje mokovanih objekata pozivanjem verify* metoda.
		 */
	}
	
	@Test
	public void testUpadetPharmacyStorageItem() {
		
		Long pharmacyId = 1L;
		Long medicationId = 2L;
		Long pharmacyStorageItemId = 3L;
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(pharmacyId);
		pharmacy.setLocation(new Location());
		
		Medication medication = new Medication();
		medication.setId(medicationId);
		medication.setForm(MedicationForm.CAPSULE);
		
		PharmacyStorageItem psiMocked = new PharmacyStorageItem();
		psiMocked.setId(pharmacyStorageItemId);
		psiMocked.setMedication(medication);
		psiMocked.setPharmacy(pharmacy);
		psiMocked.setQuantity(10);
		
		Double priceForUpdate = 500.0;
		
		List<ItemPrice> itemPrices = new ArrayList<ItemPrice>();
		ItemPrice itemPrice = new ItemPrice(priceForUpdate, true, false, new TimePeriod());
		itemPrices.add(itemPrice);
		psiMocked.setItemPrices(itemPrices);
		
		
		PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWIPDTO = new PharmacyStorageItemWithItemPricesDTO(psiMocked);
		
		// 1. Definisanje ponašanja mock objekata
		Mockito.when(pharmacyStorageItemService.findOneWithItemPrices(pharmacyStorageItemId)).thenReturn(psiMocked);
		Mockito.when(pharmacyStorageItemRepositoryMock.save(Mockito.any(PharmacyStorageItem.class))).thenReturn(psiMocked);
		Mockito.when(itemPriceServiceMock.save(Mockito.any(ItemPrice.class))).thenReturn(itemPrice);
		
		// 2. Akcija
		PharmacyStorageItem psi = pharmacyStorageItemService.updatePharmacyStorageItem(pharmacyStorageItemWIPDTO);
		Double newPrice = pharmacyStorageItemService.getCurrentPrice(psi);
		
		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
		assertEquals(newPrice, priceForUpdate);
		
	}
}
