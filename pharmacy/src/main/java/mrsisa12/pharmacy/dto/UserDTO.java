package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.User;
import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserRole;

public class UserDTO 
{
	/*
	 * Potrebna doimplementacija
	 * */
	private Long id;
	private String username;
	private String password;
	private String email;
	private String firstName;
	private String lastName;
	private Location location;
	private Gender gender;
	private UserRole userRole;
	
	public UserDTO() { }
	
	public UserDTO(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			Gender gender, UserRole userRole) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
		this.gender = gender;
		this.setUserRole(userRole);
	}
	
	public UserDTO(User user)
	{
		this(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getLocation(), user.getGender(), user.getUserRole());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	
	

}
