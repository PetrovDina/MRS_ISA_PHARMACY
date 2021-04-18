package mrsisa12.pharmacy.model;

import static javax.persistence.InheritanceType.JOINED;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import mrsisa12.pharmacy.model.enums.Gender;
import mrsisa12.pharmacy.model.enums.UserStatus;

@Entity
@Table(name = "system_user")
@Inheritance(strategy=JOINED)
public abstract class User implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "username", unique=true, nullable=false)
	private String username;
	
	@Column(name = "password", unique=false, nullable=false)
	private String password;
	
	@Column(name = "email", unique=false, nullable=false)
	private String email;
	
	@Column(name = "firstName", unique=false, nullable=false)
	private String firstName;
	
	@Column(name = "lastName", unique=false, nullable=false)
	private String lastName;
	
	@OneToOne
	private Location location;
	
	@Column(name = "gender", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "activeStatus", unique=false, nullable=false)
	@Enumerated(EnumType.STRING)
	private UserStatus activeStatus;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<UserRole> roles;
	
	@Column(name = "deleted", unique=false, nullable=false)
	private boolean deleted;
	
	public User() { }
	
	public User(Long id, String username, String password, String email, String firstName, String lastName, Location location, Gender gender,
			UserStatus activeStatus, List<UserRole> userRoles, boolean deleted) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.location = location;
		this.gender = gender;
		this.activeStatus = activeStatus;
		this.roles = userRoles;
		this.deleted = deleted;
	}
	
	/*
	public User(UserDTO user)
	{
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.firstName = user.getFirstName();
		this.lastName = user.getLastName();
		this.location = user.getLocation();
		this.gender = user.getGender();
		this.activeStatus = user.
	}
	*/
	
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
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
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
	
	public List<UserRole> getRoles() {
		return this.roles;
	}

	public void setRoles(List<UserRole> roles) {
		this.roles = roles;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return !this.isDeleted();
	}
}
