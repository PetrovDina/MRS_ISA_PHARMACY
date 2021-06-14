package mrsisa12.pharmacy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import mrsisa12.pharmacy.dto.MedicationQrDTO;
import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.util.TestUtil;

@WithMockUser(username="patient", roles= {"PATIENT"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class EPrescriptionControllerTest {
	
	private static final String URL_PREFIX = "/ePrescription";

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
	public void testBuyMedicationsQr() throws Exception {
		
		MedicationQrDTO med1 = new MedicationQrDTO(7L, 2);
		List<MedicationQrDTO> medications = new ArrayList<MedicationQrDTO>();
		medications.add(med1);
		String code = "codeTest";
		
		QrCodeDTO qrCodeDTO = new QrCodeDTO(medications, code);
		
		String json = TestUtil.json(qrCodeDTO);
		String message = "Medication/s successfully bought with the price of: 585.0,00 RSD. You have gained a discount of 10% for each medication because of your loyalty program category. Loyalty points earned with this purchase: 14.";
		
		this.mockMvc.perform(post(URL_PREFIX + "/buyMedicationsQr").contentType(contentType).content(json)
		.param("pharmacyId", "3")
		.param("username", "patient"))
		.andExpect(status().isCreated())
		.andExpect(content().string(message));
		
		message = "Qr code has been used once already.";
		this.mockMvc.perform(post(URL_PREFIX + "/buyMedicationsQr").contentType(contentType).content(json)
				.param("pharmacyId", "3")
				.param("username", "patient"))
				.andExpect(status().isForbidden())
				.andExpect(content().string(message));
	}

}
