package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Dermatologist;
import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.enums.Gender;

public class PlainEmployeeDTO {

	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private Gender gender;
	private double rating;
	private String employeeType;
	
	public PlainEmployeeDTO() { }
	
	public PlainEmployeeDTO(Long id, String username, String email, String firstName, String lastName,
			Gender gender, double rating, String employeeType) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.rating = rating;
		this.employeeType = employeeType;
	}
	
	public PlainEmployeeDTO(Employee employee) 
	{ 
		this(employee.getId(), employee.getUsername(), employee.getEmail(), employee.getFirstName(), 
				employee.getLastName(), employee.getGender(), employee.getRating(),
				(employee instanceof Dermatologist) ? "Dermatologits" : "Pharmacist");
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

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(String employeeType) {
		this.employeeType = employeeType;
	}
}
