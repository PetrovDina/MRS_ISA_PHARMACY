package mrsisa12.pharmacy.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;

import mrsisa12.pharmacy.model.ItemPrice;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.TherapyItem;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.repository.TherapyItemRepository;
import mrsisa12.pharmacy.service.LoyaltyProgramService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
import mrsisa12.pharmacy.service.TherapyItemService;
import mrsisa12.pharmacy.service.TherapyService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TherapyItemServiceTest {
	
	@Mock
	private TherapyItemRepository therapyItemRepositoryMock;
		
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
	
	@InjectMocks
	private TherapyItemService therapyItemServiceMock;
	
	@Test(expected=AssertionError.class)
	@Transactional
	public void testTherapyItemDayCountIncorrect(){
        TherapyItem therapyItem = new TherapyItem();
        therapyItem.setTherapyDuration(-1);
        Assert.assertEquals(0, therapyItem.getTherapyDuration());
    }

	@Test
    public void testTherapyItemDayCountCorrect(){
        TherapyItem therapyItem = new TherapyItem();
        therapyItem.setTherapyDuration(3);
        Assert.assertEquals(3, therapyItem.getTherapyDuration());
    }

    
    
    @Test(expected=IllegalArgumentException.class)
	@Transactional
	@Rollback(true)
    public void testTherapyItemQuantityIncorrect(){
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
    	
    	Therapy therapyMock = new Therapy();
    	therapyMock.setId(1l);
    	therapyMock.setPatient(patientMock);
    	therapyMock.setCode("1234567890");
    	therapyMock.setPrescribedDate(new Date());
    	therapyMock.setPharmacy(pharmacyMock);
    	therapyMock.setPatient(patientMock);
    	
    	Medication medicationMock = new Medication();
		medicationMock.setId(1l);
		medicationMock.setName("Probiotik");
		medicationMock.setRating(4.0);
		medicationMock.setLoyaltyPoints(5);
		
		PharmacyStorageItem psiMock = new PharmacyStorageItem();
		psiMock.setCounter(0);
		psiMock.setDeleted(false);
		psiMock.setId(1l);
		psiMock.setMedication(medicationMock);
		psiMock.setPharmacy(pharmacyMock);
		psiMock.setQuantity(10);
    	
        TherapyItem therapyItemMock = new TherapyItem();
        therapyItemMock.setQuantity(100);
        therapyItemMock.setTherapyDuration(3);
        therapyItemMock.setEPrescription(therapyMock);
        therapyItemMock.setMedication(medicationMock);
        therapyItemMock.setMedicationPrice(1000);
        therapyItemMock.setId(1l);
        
        when(pharmacyStorageItemServiceMock.findOne(psiMock.getId())).thenReturn(psiMock);
		when(therapyServiceMock.findOneWithPatient(therapyMock.getId())).thenReturn(therapyMock);
		when(loyaltyProgramServiceMock.getFinalPrice(1000.0, patientMock)).thenReturn(1000.0);
		when(therapyItemRepositoryMock.save(therapyItemMock)).thenReturn(therapyItemMock);
		when(therapyItemRepositoryMock.save(therapyItemMock)).thenReturn(therapyItemMock);
        
        therapyItemServiceMock.savePrescriptionItem(psiMock.getId().toString(), String.valueOf(therapyItemMock.getQuantity()), String.valueOf(therapyItemMock.getTherapyDuration()) ,
        		pharmacyMock.getId().toString(), therapyMock.getId().toString());
    }
    
    @Test
	@Transactional
	@Rollback(true)
    public void testTherapyItemQuantityCorrect(){
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
    	
    	Therapy therapyMock = new Therapy();
    	therapyMock.setId(1l);
    	therapyMock.setPatient(patientMock);
    	therapyMock.setCode("1234567890");
    	therapyMock.setPrescribedDate(new Date());
    	therapyMock.setPharmacy(pharmacyMock);
    	therapyMock.setPatient(patientMock);
    	
    	Medication medicationMock = new Medication();
		medicationMock.setId(1l);
		medicationMock.setName("Probiotik");
		medicationMock.setRating(4.0);
		medicationMock.setLoyaltyPoints(5);
		
		PharmacyStorageItem psiMock = new PharmacyStorageItem();
		psiMock.setCounter(0);
		psiMock.setDeleted(false);
		psiMock.setId(1l);
		psiMock.setMedication(medicationMock);
		psiMock.setPharmacy(pharmacyMock);
		psiMock.setQuantity(10);
		List<ItemPrice> list = new ArrayList<ItemPrice>();
		list.add(new ItemPrice(100.0, true, false, new TimePeriod()));
		psiMock.setItemPrices(list);
    	
        TherapyItem therapyItemMock = new TherapyItem();
        therapyItemMock.setQuantity(1);
        therapyItemMock.setTherapyDuration(3);
        therapyItemMock.setEPrescription(therapyMock);
        therapyItemMock.setMedication(medicationMock);
        therapyItemMock.setMedicationPrice(1000);
        therapyItemMock.setId(1l);
        
        when(pharmacyStorageItemServiceMock.findOne(psiMock.getId())).thenReturn(psiMock);
		when(therapyServiceMock.findOneWithPatient(therapyMock.getId())).thenReturn(therapyMock);
		when(loyaltyProgramServiceMock.getFinalPrice(100.0, patientMock)).thenReturn(1000.0);
		therapyItemMock.setMedicationPrice(1000.0);
		when(therapyItemServiceMock.save(therapyItemMock)).thenReturn(therapyItemMock);
		psiMock.setQuantity(psiMock.getQuantity()-1);
		when(pharmacyStorageItemServiceMock.save(psiMock)).thenReturn(psiMock);
		//when(patientServiceMock.addPointsAndUpdateCategory(patientMock, 100)).thenReturn(null);
        
        therapyItemServiceMock.savePrescriptionItem(psiMock.getId().toString(), String.valueOf(therapyItemMock.getQuantity()), String.valueOf(therapyItemMock.getTherapyDuration()) ,
        		pharmacyMock.getId().toString(), therapyMock.getId().toString());
    }

}
