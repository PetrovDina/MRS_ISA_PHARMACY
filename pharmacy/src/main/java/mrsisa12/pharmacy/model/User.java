package mrsisa12.pharmacy.model;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import mrsisa12.pharmacy.model.enums.UserStatus;
import mrsisa12.pharmacy.model.enums.UserRole;



@Entity
@Table(name = "system_user")
@Inheritance(strategy=JOINED)
public abstract class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "username", unique=true, nullable=false)
	private String username;
	
	@Column(name = "password", unique=true, nullable=false)
	private String password;
	
	@Column(name = "email", unique=false, nullable=false)
	private String email;
	
	@Column(name = "firstName", unique=false, nullable=false)
	private String firstName;
	
	@Column(name = "lastName", unique=false, nullable=false)
	private String lastName;
	
	@OneToOne
	private Location location;
	
	@Column(name = "activeStatus", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private UserStatus activeStatus;
	
	@Column(name = "userRole", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	
	@Column(name = "deleted", unique=false, nullable=false)
	private boolean deleted;
	
	public User() { }
	
	public User(Long id, String username, String password, String email, String firstName, String lastName, Location location,
			UserStatus activeStatus, UserRole userRole, boolean deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
		this.activeStatus = activeStatus;
		this.userRole = userRole;
		this.deleted = deleted;
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
	
	public Location getLocation() {
		return location;
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public UserStatus getActiveStatus() {
		return activeStatus;
	}
	
	public void setActiveStatus(UserStatus activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
	
	

}
