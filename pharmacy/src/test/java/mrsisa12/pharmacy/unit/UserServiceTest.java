package mrsisa12.pharmacy.unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.model.User;
import mrsisa12.pharmacy.repository.UserRepository;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

	@Mock
	private UserRepository userRepositoryMock;
	
	@Mock
	private User userMock;
	
	@Mock
	private LocationService locationServiceMock;
	
	@InjectMocks
	private UserService userService;
	
	@Test
	public void testUpdateUser() {
		
		userMock = new SystemAdmin();
		userMock.setId(1L);
		userMock.setUsername("veljko");
		userMock.setFirstName("Veljko");
		userMock.setLastName("Tosic");
		
		Location l = new Location();
		l.setCity("Zajecar");
		l.setStreet("Ljube Nesica");
		l.setStreetNum(2);
		l.setZipcode("19000");
		userMock.setLocation(l);
		
		UserDTO userDTO = new UserDTO(userMock);
		userDTO.setFirstName("Tosa");
		userDTO.setLastName("Veljkovic");
		userDTO.getLocation().setStreet("Cupiceva");
		userDTO.getLocation().setStreetNum(2);
		
		when(userRepositoryMock.findByUsername(userMock.getUsername())).thenReturn((userMock));
		
		userMock = userService.updateUser(userDTO);
		
		assertEquals(userMock.getFirstName(), "Tosa");
		assertEquals(userMock.getLastName(), "Veljkovic");
		assertEquals(userMock.getLocation().getCity(), "Zajecar");
		assertEquals(userMock.getLocation().getStreet(), "Cupiceva");
		assertEquals(userMock.getLocation().getStreetNum(), 2);
	}
	
}
