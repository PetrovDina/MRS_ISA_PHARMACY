package mrsisa12.pharmacy.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import mrsisa12.pharmacy.dto.PatientDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.util.TestUtil;


@WithMockUser(username="patient", roles= {"PATIENT"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientControllerTest {

	private static final String URL_PREFIX = "/patient";

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
	public void testUpdatePatient() throws Exception {
		
		PatientDTO patient = new PatientDTO();
		patient.setId(2l);
		patient.setUsername("patient");
		patient.setFirstName("Branislava");
		patient.setLastName("Popov");
		patient.setEmail("branislava@gmail.com");
		patient.setGender(Gender.FEMALE);
		Location l = new Location();
		l.setCity("Zrenjanin");
		l.setStreet("Prvomajska");
		l.setStreetNum(2);
		l.setZipcode("23000");
		patient.setLocation(l);

		String json = TestUtil.json(patient);

		 mockMvc.perform(put(URL_PREFIX).contentType(contentType).content(json))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType));


	}
	

	

	@Test
	public void testGetAllPatients() throws Exception {
		mockMvc.perform(get(URL_PREFIX + "/all")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.[*].username").value(hasItem("patient")))
		.andExpect(jsonPath("$.[*].firstName").value(hasItem("Patient")))
		.andExpect(jsonPath("$.[*].lastName").value(hasItem("Patientic")));

	}


}
