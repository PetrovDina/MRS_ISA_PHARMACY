package mrsisa12.pharmacy.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

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

@WithMockUser(username="mirko", roles= {"PHARMACIST"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class TherapyItemControllerTest {
	
	private static final String URL_PREFIX = "/therapyItem";

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
	public void testAddPrescriptionValid() throws Exception {
		
		 mockMvc.perform(get(URL_PREFIX + "/addPrescription")
		.param("storageId", "1")
		.param("quantity", "3")
		.param("duration", "7")
		.param("pharmacyId", "1")
		.param("ePrescriptionId", "1"))
		.andExpect(status().isCreated())
		.andExpect(content().string("ok"));

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testAddPrescriptionInvalid() throws Exception {
		
		
		 mockMvc.perform(get(URL_PREFIX + "/addPrescription")
		.param("storageId", "1")
		.param("quantity", "30000")
		.param("duration", "7")
		.param("pharmacyId", "1")
		.param("ePrescriptionId", "1"))
		.andExpect(status().isBadRequest())
		.andExpect(content().string("Unfortunately, we don't have enough medicine in storage. Please try again later.")); 

	}
	

}
