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


@WithMockUser(username="patient", roles= {"PATIENT"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class MedicationControllerTest {

	private static final String URL_PREFIX = "/med";

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
	public void testNewMedicationRating() throws Exception {
		
		 mockMvc.perform(get(URL_PREFIX + "/rateMedication")
		.param("patientUsername", "patient")
		.param("medicationId", "1")
		.param("ratedValue", "5.0"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(content().json("5.0"));


	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testInvalidMedicationRating() throws Exception {
		
		
		 mockMvc.perform(get(URL_PREFIX + "/rateMedication")
		.param("patientUsername", "patient")
		.param("medicationId", "1")
		.param("ratedValue", "-5.0"))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(contentType));
		 


	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testChangeMedicationRating() throws Exception {
		
		
		 mockMvc.perform(get(URL_PREFIX + "/rateMedication")
		.param("patientUsername", "patient")
		.param("medicationId", "1")
		.param("ratedValue", "5.0"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(content().json("5.0"));
		 
		 mockMvc.perform(get(URL_PREFIX + "/rateMedication")
		.param("patientUsername", "patient")
		.param("medicationId", "1")
		.param("ratedValue", "3.0"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(content().json("3.0"));

	}
	

	@Test
	public void testGetAllMedications() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].name").value(hasItem("Probiotik")))
		.andExpect(jsonPath("$.[*].form").value(hasItem("PILL")));

	}


}
