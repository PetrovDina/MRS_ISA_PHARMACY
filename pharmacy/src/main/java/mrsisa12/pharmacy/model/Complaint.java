package mrsisa12.pharmacy.model;

import static javax.persistence.InheritanceType.JOINED;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "complaint")
@Inheritance(strategy=JOINED)
public abstract class Complaint {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private Patient patient;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "system_admin_id", referencedColumnName = "id")
	private SystemAdmin systemAdmin;
	
	@Column(nullable=false)
	private String content;
	
	@Column(nullable=true)
	private String response;
	
	@Version
	private Long version;
	
	public Complaint() { }

	public Complaint(Long id, Patient patient, SystemAdmin systemAdmin, String content, String response) {
		super();
		this.id = id;
		this.patient = patient;
		this.systemAdmin = systemAdmin;
		this.content = content;
		this.response = response;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public SystemAdmin getSystemAdmin() {
		return systemAdmin;
	}

	public void setSystemAdmin(SystemAdmin systemAdmin) {
		this.systemAdmin = systemAdmin;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
}
