package mrsisa12.pharmacy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import mrsisa12.pharmacy.dto.PharmacyAdminDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyAdmin;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.util.TestUtil;


@WithMockUser(username="admin", roles= {"SYSTEM_ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class PharmacyAdminControllerTest {

	private static final String URL_PREFIX = "/pharmacyAdmin";

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
	public void testGetPharmacyAdminByUsername() throws Exception {
		
		PharmacyAdmin pa = new PharmacyAdmin();
		pa.setUsername("padmin");
		
		mockMvc.perform(get(URL_PREFIX + "/" + "padmin")).andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.username").value("padmin"))
		.andExpect(jsonPath("$.firstName").value("Padmin"))
		.andExpect(jsonPath("$.lastName").value("Padminic"));

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testCreatePharmacyAdmin() throws Exception {
		
		PharmacyAdminDTO pa = new PharmacyAdminDTO();
		pa.setUsername("padmin2");
		pa.setPassword("padmintest");
		pa.setEmail("peadmin@gmail.com");
		pa.setFirstName("Pera");
		pa.setLastName("Peric");
		pa.setLocation(new Location(45.2413873, 19.8435649, "Bulevar Oslobođenja","Novi Sad", "21000", 135));
		pa.setGender(Gender.MALE);
		pa.setPharmacyId(1L);
		
		String json = TestUtil.json(pa);

		mockMvc.perform(
				post(URL_PREFIX + "/create")
				.contentType(contentType)
				.content(json))
		.andExpect(status()
				.isCreated());

	}
	
}
