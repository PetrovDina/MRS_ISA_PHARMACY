package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.Gender;

public class EmployeeDTO extends UserDTO {

	private double rating;

	public EmployeeDTO() {
	}

	public EmployeeDTO(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			Gender gender, double rating) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.rating = rating;
	}

	public EmployeeDTO(Employee employee) {
		this(employee.getId(), employee.getUsername(), employee.getPassword(), employee.getEmail(), employee.getFirstName(),
				employee.getLastName(), employee.getLocation(), employee.getGender(), employee.getRating());
	}
	
	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
