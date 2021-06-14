package mrsisa12.pharmacy.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

import mrsisa12.pharmacy.dto.MedicationCreationDTO;
import mrsisa12.pharmacy.dto.MedicationDTO;
import mrsisa12.pharmacy.model.enums.MedicationForm;
import mrsisa12.pharmacy.util.TestUtil;


@WithMockUser(username="patient", roles= {"PATIENT", "SYSTEM_ADMIN"})
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
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testMedicationCreation() throws Exception {
		
		MedicationDTO alt1 = new MedicationDTO(1L, "Probiotik", "Ivancic i sinovi", false, MedicationForm.PILL, 0.0);
		MedicationDTO alt2 = new MedicationDTO(5L, "Panklav", "Krka", true, MedicationForm.CAPSULE, 0.0);
		List<MedicationDTO> alternatives = new ArrayList<MedicationDTO>(); 
		alternatives.add(alt1); alternatives.add(alt2);
		
		MedicationCreationDTO med = new MedicationCreationDTO(8L, "Probiotik forte", "Uniprom-M", false, MedicationForm.CAPSULE, alternatives, "Probiotik forte opis", "Svasta nesto ima.", 0.0, 4);
		
		String json = TestUtil.json(med);
		this.mockMvc.perform(post(URL_PREFIX + "/create").contentType(contentType).content(json)).andExpect(status().isCreated());
	}


}
