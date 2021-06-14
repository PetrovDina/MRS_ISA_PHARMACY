package mrsisa12.pharmacy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import mrsisa12.pharmacy.dto.ItemPriceDTO;
import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.dto.TimePeriodDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemWithItemPricesDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Medication;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.enums.MedicationForm;
import mrsisa12.pharmacy.util.TestUtil;


@WithMockUser(username="padmin", roles= {"PHARMACY_ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyStorageItemControllerTest {

	private static final String URL_PREFIX = "/pharmacyStorageItem";

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}


	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePharmacyStorageItem() throws Exception {
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1L);
		pharmacy.setLocation(new Location());
		
		Medication medication = new Medication();
		medication.setId(1L);
		medication.setForm(MedicationForm.CAPSULE);
		
		PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWithItemPricesDTO = new PharmacyStorageItemWithItemPricesDTO();
		pharmacyStorageItemWithItemPricesDTO.setId(1L);
		pharmacyStorageItemWithItemPricesDTO.setMedication(new MedicationDTO(medication));
		pharmacyStorageItemWithItemPricesDTO.setPharmacy(new PharmacyDTO(pharmacy));
		pharmacyStorageItemWithItemPricesDTO.setQuantity(10);
		
		List<ItemPriceDTO> itemPrices = new ArrayList<ItemPriceDTO>();
		itemPrices.add(new ItemPriceDTO(1L, 500, true, false, new TimePeriodDTO()));
		pharmacyStorageItemWithItemPricesDTO.setItemPrices(itemPrices);
		
		String json = TestUtil.json(pharmacyStorageItemWithItemPricesDTO);

		mockMvc.perform(
				post(URL_PREFIX)
				.contentType(contentType)
				.content(json))
		.andExpect(status()
				.isCreated());

	}
	

	

	@Test
	public void testUpdatePharmacyStorageItem() throws Exception {
		
		Pharmacy pharmacy = new Pharmacy();
		pharmacy.setId(1L);
		pharmacy.setLocation(new Location());
		
		Medication medication = new Medication();
		medication.setId(1L);
		medication.setForm(MedicationForm.CAPSULE);
		
		PharmacyStorageItemWithItemPricesDTO pharmacyStorageItemWithItemPricesDTO = new PharmacyStorageItemWithItemPricesDTO();
		pharmacyStorageItemWithItemPricesDTO.setId(1L);
		pharmacyStorageItemWithItemPricesDTO.setMedication(new MedicationDTO(medication));
		pharmacyStorageItemWithItemPricesDTO.setPharmacy(new PharmacyDTO(pharmacy));
		pharmacyStorageItemWithItemPricesDTO.setQuantity(10);
		
		List<ItemPriceDTO> itemPrices = new ArrayList<ItemPriceDTO>();
		itemPrices.add(new ItemPriceDTO(1L, 500, true, false, new TimePeriodDTO()));
		pharmacyStorageItemWithItemPricesDTO.setItemPrices(itemPrices);
		
		String json = TestUtil.json(pharmacyStorageItemWithItemPricesDTO);

		 mockMvc.perform(
				 put(URL_PREFIX)
				 .contentType(contentType)
				 .content(json))
		.andExpect(status()
				.isOk())
		.andExpect(content()
				.contentType(contentType));

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDeletePharmacyStorageItem() throws Exception {
		mockMvc.perform(delete(URL_PREFIX + "/" + 1L)).andExpect(status().isOk());
	}


}
