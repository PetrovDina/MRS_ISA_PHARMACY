package mrsisa12.pharmacy.dto;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.TimePeriod;
import mrsisa12.pharmacy.model.enums.Gender;

public class EmployeeDTO extends UserDTO {

	private TimePeriod workTime;
	private double rating;

	public EmployeeDTO() {
	}

	public EmployeeDTO(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			Gender gender, TimePeriod workTime, double rating) {
		super(id, username, password, email, firstName, lastName, location, gender);
		this.workTime = workTime;
		this.rating = rating;
	}

	public EmployeeDTO(Employee employee) {
		this(employee.getId(), employee.getUsername(), employee.getPassword(), employee.getEmail(), employee.getFirstName(),
				employee.getLastName(), employee.getLocation(), employee.getGender(),
				employee.getWorkTime(), employee.getRating());
	}

	public TimePeriod getWorkTime() {
		return workTime;
	}

	public void setWorkTime(TimePeriod workTime) {
		this.workTime = workTime;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

}
