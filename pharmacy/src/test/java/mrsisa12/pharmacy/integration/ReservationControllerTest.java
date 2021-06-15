package mrsisa12.pharmacy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class ReservationControllerTest {
	
	private static final String URL_PREFIX = "/reservation";
	
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
	public void testConfirmPickup() throws Exception {
		
		 mockMvc.perform(get(URL_PREFIX + "/confirmPickup")
		.param("rId", "3"))
		.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchPickupValid() throws Exception {
		
		 mockMvc.perform(get(URL_PREFIX + "/pickup")
		.param("rCode", "rsrvtion01")
		.param("pharmId", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		;
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSearchPickupInvalid() throws Exception {
		
		 mockMvc.perform(get(URL_PREFIX + "/pickup")
		.param("rCode", "rsrvtion")
		.param("pharmId", "1"))
		.andExpect(status().isNotFound());
	}

}
