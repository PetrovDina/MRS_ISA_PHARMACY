package mrsisa12.pharmacy.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import mrsisa12.pharmacy.model.enums.EPrescriptionStatus;

@Entity
public class EPrescription {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private Patient patient;
	
	@Column(name = "prescribedDate", nullable = false)
	private Date prescribedDate;
	
	@Column(name = "code", nullable = false, length =  10)
	private String code;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status", nullable = false)
	private EPrescriptionStatus status;
		
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "ePrescription")
	private List<PrescriptionItem> prescriptionItems;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pharmacy_id", referencedColumnName = "id", nullable = true)
	private Pharmacy pharmacy;
	
	public EPrescription() {
		super();
	}



	public EPrescription(Long id, Patient patient, Date prescribedDate, String code, EPrescriptionStatus status,
			List<PrescriptionItem> prescriptionItems) {
		super();
		this.id = id;
		this.patient = patient;
		this.prescribedDate = prescribedDate;
		this.code = code;
		this.status = status;
		this.prescriptionItems = prescriptionItems;
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

	public Date getPrescribedDate() {
		return prescribedDate;
	}

	public void setPrescribedDate(Date prescribedDate) {
		this.prescribedDate = prescribedDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<PrescriptionItem> getPrescriptionItems() {
		if(prescriptionItems == null)
			prescriptionItems = new ArrayList<>();
		return prescriptionItems;
	}

	public void setPrescriptionItems(List<PrescriptionItem> newPrescriptionItems) {
		removeAllPrescriptionItems();
		for (Iterator<PrescriptionItem> iter = newPrescriptionItems.iterator(); iter.hasNext();)
			addPrescriptionItem(iter.next());
	}
	
	public Iterator<PrescriptionItem> getIteratorPrescriptionItem() {
		if (prescriptionItems == null)
			prescriptionItems = new ArrayList<>();
		return prescriptionItems.iterator();
	}
	
	public void removeAllPrescriptionItems() {
		if (prescriptionItems != null) {
			PrescriptionItem oldItem;
			for (Iterator<PrescriptionItem> iter = getIteratorPrescriptionItem(); iter.hasNext();) {
				oldItem = iter.next();
				iter.remove();
			}
		}
	}
	
	public void removePrescriptionItem(PrescriptionItem oldPrescriptionItem) {
		if (oldPrescriptionItem == null)
			return;
		if (this.prescriptionItems != null && this.prescriptionItems.contains(oldPrescriptionItem)) {
			this.prescriptionItems.remove(oldPrescriptionItem);
		}
	}
	
	public void addPrescriptionItem(PrescriptionItem newPrescriptionItem) {
		if (newPrescriptionItem == null)
			return;
		if (this.prescriptionItems == null)
			this.prescriptionItems = new ArrayList<>();
		if (!this.prescriptionItems.contains(newPrescriptionItem)) {
			this.prescriptionItems.add(newPrescriptionItem);
		}
	}


	public EPrescriptionStatus getStatus() {
		return status;
	}


	public void setStatus(EPrescriptionStatus status) {
		this.status = status;
	}


	public Pharmacy getPharmacy() {
		return pharmacy;
	}


	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}
	
	
}
