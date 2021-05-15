package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyWithEmploymentsDTO;
import mrsisa12.pharmacy.dto.pharmacy.PlainPharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.AppointmentPriceCatalog;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.ReservationService;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {

	@Autowired
	private PharmacyService pharmacyService;
	
	@Autowired
	private PatientService patientService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private AppointmentService appointmentService;

	@GetMapping(value = "/all")
	public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {

		List<Pharmacy> pharmacies = pharmacyService.findAll();

		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Pharmacy m : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(m));
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/searchPharmacies")
	public ResponseEntity<List<PharmacyDTO>> searchPharmacies(@RequestParam String query) {

		List<Pharmacy> pharmacies = pharmacyService.findByQuery(query);

		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Pharmacy m : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(m));
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PharmacyDTO>> getPharmaciesPage(Pageable page) {

		Page<Pharmacy> pharmacies = pharmacyService.findAll(page);

		List<PharmacyDTO> pharmaciesDTO = new ArrayList<>();
		for (Pharmacy m : pharmacies) {
			pharmaciesDTO.add(new PharmacyDTO(m));
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<PharmacyDTO> getPharmacy(@PathVariable Long id) {

		Pharmacy pharmacy = pharmacyService.findOne(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PharmacyDTO(pharmacy), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/withEmployments")
	public ResponseEntity<PharmacyWithEmploymentsDTO> getPharmacyWitEmployments(@PathVariable Long id) {

		Pharmacy pharmacy = pharmacyService.findOneWithEmployments(id);

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(new PharmacyWithEmploymentsDTO(pharmacy), HttpStatus.OK);
	}

	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<PharmacyDTO> savePharmacy(@RequestBody PharmacyDTO pharmacyDTO) {

		Pharmacy pharmacy = new Pharmacy();

		pharmacy.setName(pharmacyDTO.getName());
		pharmacy.setLocation(pharmacyDTO.getLocation());
		// prilikom kreiranja postavljamo cijenovnik
		AppointmentPriceCatalog appointmentPriceCatalog = new AppointmentPriceCatalog(
				pharmacyDTO.getAppointmentPriceCatalog().getExaminationPrice(),
				pharmacyDTO.getAppointmentPriceCatalog().getConsultationPrice());
		pharmacy.setAppointmentPriceCatalog(appointmentPriceCatalog);

		pharmacy = pharmacyService.save(pharmacy);
		return new ResponseEntity<>(new PharmacyDTO(pharmacy), HttpStatus.CREATED);
	}

	@PutMapping(consumes = "application/json")
	public ResponseEntity<PharmacyDTO> updatePharmacy(@RequestBody PharmacyDTO pharmacyDTO) {

		Pharmacy pharmacy = pharmacyService.findOne(pharmacyDTO.getId());

		if (pharmacy == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		pharmacy.setName(pharmacyDTO.getName());
		pharmacy.setLocation(pharmacyDTO.getLocation());
		
		pharmacy = pharmacyService.save(pharmacy);
		return new ResponseEntity<>(new PharmacyDTO(pharmacy), HttpStatus.CREATED);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deletePharmacy(@PathVariable Long id) {

		Pharmacy pharmacy = pharmacyService.findOne(id);
		;

		if (pharmacy != null) {
			pharmacyService.remove(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/findName")
	public ResponseEntity<List<PharmacyDTO>> getPharmacyByName(@RequestParam String name) {

		List<Pharmacy> pharmacies = pharmacyService.findByNameAllIgnoringCase(name);

		List<PharmacyDTO> pharmacyDTO = new ArrayList<>();
		for (Pharmacy s : pharmacies) {
			pharmacyDTO.add(new PharmacyDTO(s));
		}
		return new ResponseEntity<>(pharmacyDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{pharmacyId}/pharmacyStorageItemsAndPrices")
	public ResponseEntity<List<PharmacyStorageItemDTO>> getPharmacyStorageItemsAndPrices(
			@PathVariable Long pharmacyId) {

		Pharmacy pharmacy = pharmacyService.findOneWithStorageItems(pharmacyId);

		List<PharmacyStorageItem> pharmacyStorageItems = pharmacy.getPharmacyStorageItems();
		List<PharmacyStorageItemDTO> pharmacyStorageItemsDTO = new ArrayList<>();
		for (PharmacyStorageItem e : pharmacy.getPharmacyStorageItems()) {
			PharmacyStorageItemDTO pharmacyStorageItemDTO = new PharmacyStorageItemDTO();
			pharmacyStorageItemDTO.setId(e.getId());
			pharmacyStorageItemDTO.setQuantity(e.getQuantity());

			pharmacyStorageItemsDTO.add(pharmacyStorageItemDTO);
		}
		return new ResponseEntity<>(pharmacyStorageItemsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{username}/complaints")
	public ResponseEntity<List<PlainPharmacyDTO>> getPharmaciesForComplaint(
			@PathVariable String username) {

		Patient patient = patientService.findByUsername(username);
		List<Pharmacy> pharmacies = new ArrayList<Pharmacy>();
		
		List<Reservation> reservations = reservationService.findAllByPatient(patient.getUsername());
		for (Reservation reservation : reservations) 
		{	
			if(!pharmacyService.containsPharmacy(pharmacies, reservation.getPharmacy()))
				if(reservation.getStatus() == ReservationStatus.COMPLETED)
					pharmacies.add(reservation.getPharmacy());
		}
		
		List<Appointment> appointemts = appointmentService.findAllByPatientId(patient.getId());
		for (Appointment appointment : appointemts) 
		{
			if(!pharmacyService.containsPharmacy(pharmacies, appointment.getPharmacy()))
				if(appointment.getStatus() == AppointmentStatus.CONCLUDED)
					pharmacies.add(appointment.getPharmacy());
		}
		
		// Dodati i za recepte
		
		List<PlainPharmacyDTO> pharmaciesDTO = new ArrayList<PlainPharmacyDTO>();
		for (Pharmacy pharmacy : pharmacies) 
		{
			pharmaciesDTO.add(new PlainPharmacyDTO(pharmacy));
		}

		return new ResponseEntity<>(pharmaciesDTO, HttpStatus.OK);
	}
	

}
