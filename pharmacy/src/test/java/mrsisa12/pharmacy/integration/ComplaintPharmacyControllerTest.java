package mrsisa12.pharmacy.integration;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

@WithMockUser(username="admin", roles= {"SYSTEM_ADMIN"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintPharmacyControllerTest {
	
	private static final String URL_PREFIX = "/complaintPharmacy";
	
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
	public void testGetPharmacyComplaintForResponse() throws Exception {
		
		mockMvc.perform(get(URL_PREFIX + "/all/forResponse"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(1)))
		.andExpect(jsonPath("$.[*].pharmacyName").value(hasItem("Apoteka Jankovic")))
		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].pharmacyName").value(hasItem("Benu")))
		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(2)))
		.andExpect(jsonPath("$.[*].pharmacyName").value(hasItem("Srbotrade")));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetPharmacyComplaintResponsed() throws Exception {
		
		mockMvc.perform(get(URL_PREFIX + "/all/" + "admin"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(3)))
		.andExpect(jsonPath("$.[*].pharmacyName").value(hasItem("Benu")))
		.andExpect(jsonPath("$.[*].content").value(hasItem("Farmaceut hteo da vrsi psihicko nasilje nadamnome.")))
		.andExpect(jsonPath("$.[*].response").value(hasItem("Farmaceut kaze nije.")))
		
		.andExpect(jsonPath("$.[*].pharmacyId").value(hasItem(2)))
		.andExpect(jsonPath("$.[*].pharmacyName").value(hasItem("Srbotrade")))
		.andExpect(jsonPath("$.[*].content").value(hasItem("Lekovima istekao rok.")))
		.andExpect(jsonPath("$.[*].response").value(hasItem("Ma to nije tacno.")));
	}

}
