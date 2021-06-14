package mrsisa12.pharmacy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.dto.UserDTO;
import mrsisa12.pharmacy.model.User;
import mrsisa12.pharmacy.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LocationService locationService;

	
	// Funkcija koja na osnovu username-a iz baze vraca objekat User-a
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		User user = userRepository.findByUsername(username);
		if (user == null) 
		{
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
		} 
		else 
		{
			return user;
		}
	}

	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username);
	}
	
	public User updateUser(UserDTO userDTO)
	{
		User user = userRepository.findByUsername(userDTO.getUsername());

		if (user == null) 
		{
			return null;
		}

		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setLocation(userDTO.getLocation());
		
		userRepository.save(user);
		locationService.save(user.getLocation());
		
		return user;
	}
	
	public void save(User user) {
		userRepository.save(user);
	}
}
