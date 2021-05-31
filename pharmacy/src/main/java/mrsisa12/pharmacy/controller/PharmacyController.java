package mrsisa12.pharmacy.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.LocationDTO;
import mrsisa12.pharmacy.dto.MedicationQrDTO;
import mrsisa12.pharmacy.dto.PharmacyWithMedicationsPriceQrDTO;
import mrsisa12.pharmacy.dto.QrCodeDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacy.PharmacyWithEmploymentsDTO;
import mrsisa12.pharmacy.dto.pharmacy.PlainPharmacyDTO;
import mrsisa12.pharmacy.dto.pharmacyStorageItem.PharmacyStorageItemDTO;
import mrsisa12.pharmacy.dto.report.ReportDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.AppointmentPriceCatalog;
import mrsisa12.pharmacy.model.Location;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyRating;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Reservation;
import mrsisa12.pharmacy.model.enums.AppointmentStatus;
import mrsisa12.pharmacy.model.enums.ReservationStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.LocationService;
import mrsisa12.pharmacy.service.PatientService;
import mrsisa12.pharmacy.service.PharmacyRatingService;
import mrsisa12.pharmacy.service.PharmacyService;
import mrsisa12.pharmacy.service.PharmacyStorageItemService;
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
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private PharmacyRatingService pharmacyRatingService;

	@Autowired
	private PharmacyStorageItemService pharmacyStorageItemService;
	
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

	@PreAuthorize("hasRole('SYSTEM_ADMIN')")
	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<PharmacyDTO> savePharmacy(@RequestBody PharmacyDTO pharmacyDTO) {

		Pharmacy pharmacy = new Pharmacy();

		pharmacy.setName(pharmacyDTO.getName());
		pharmacy.setLocation(new Location(pharmacyDTO.getLocation()));

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
		// provjeri da li ta lokacija postoji
		Location location = locationService.findOneByLatAndLong(pharmacyDTO.getLocation().getLatitude(), pharmacyDTO.getLocation().getLongitude());
		if(location == null) {
			LocationDTO locationDTO = pharmacyDTO.getLocation();
			// kreiramo novu
			location = new Location(locationDTO.getLatitude(), locationDTO.getLongitude(), locationDTO.getStreet(), 
					locationDTO.getCity(), locationDTO.getZipcode(), locationDTO.getStreetNum());
			location = locationService.save(location);
		}
		pharmacy.setLocation(location);
		
		// katalog cijena
		pharmacy.setAppointmentPriceCatalog(pharmacyDTO.getAppointmentPriceCatalog());
		
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
	
	@PreAuthorize("hasRole('PATIENT')")
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
	
	@GetMapping(value = "/reportPharmacyRevenueYear")
	public ResponseEntity<ReportDTO> reportAppointmentYear(@RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = pharmacyService.getAllMedicationConsumptedByYear(year, pharmacyService.findOne(pharmacyId));
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportPharmacyRevenueQuarter")
	public ResponseEntity<ReportDTO> reportAppointmentQuarter(@RequestParam String quarter, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = pharmacyService.getAllMedicationConsumptedByQuarter(quarter, year, pharmacyService.findOne(pharmacyId), null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/reportPharmacyRevenueMonth")
	public ResponseEntity<ReportDTO> reportAppointmentMonth(@RequestParam String period, @RequestParam String year, @RequestParam Long pharmacyId) {
		HashMap<String, Integer> data = pharmacyService.getAllMedicationConsumptedByMonthInYear(period, year, pharmacyService.findOne(pharmacyId), null);
		return new ResponseEntity<>(new ReportDTO(data), HttpStatus.OK);
	}
	
	@GetMapping(value = "/getRating")
	public ResponseEntity<Double> getRating(
			@RequestParam String patientUsername, @RequestParam Long pharmacyId) {


		PharmacyRating rating = pharmacyRatingService.findOneByPatientAndPharmacy(patientUsername, pharmacyId);
		
		if (rating == null) {
			return new ResponseEntity<Double>(0.0, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Double>(rating.getRating(), HttpStatus.OK);	
		}

	}
	
	@PostMapping(value = "/getQrSearch", consumes = "application/json")
	public ResponseEntity<List<PharmacyWithMedicationsPriceQrDTO>> getPharmaciesByQrSearch(@RequestBody QrCodeDTO medications) {

		List<PharmacyWithMedicationsPriceQrDTO> pharmacyDTOs = new ArrayList<PharmacyWithMedicationsPriceQrDTO>();
		
		Map<Long, Double>  pharmaciesWithPrice   = new HashMap<Long, Double>();
		Map<Long, Integer> pharmaciesWithCounter = new HashMap<Long, Integer>();
		
		for (MedicationQrDTO med : medications.getMedications()) 
		{
			List<PharmacyStorageItem> pharmacyStorageItems = pharmacyStorageItemService.findAllWithCurrentPriceByMedication(med.getId());
			
			for (PharmacyStorageItem item : pharmacyStorageItems) 
			{
				
				if(item.getQuantity() >= med.getQuantity())
				{
					Long pharmacyId = item.getPharmacy().getId();
					Double newPrice = pharmacyStorageItemService.getCurrentPrice(item) * med.getQuantity();
					
					if(pharmaciesWithCounter.containsKey(pharmacyId))
					{
						pharmaciesWithCounter.put(pharmacyId, pharmaciesWithCounter.get(pharmacyId) + 1);
						pharmaciesWithPrice.put(pharmacyId, pharmaciesWithPrice.get(pharmacyId) + newPrice);
					}
					else
					{
						pharmaciesWithCounter.put(pharmacyId, 1);
						pharmaciesWithPrice.put(pharmacyId, newPrice);
					}
				}
				
			}
		}
		
		for (Long id : pharmaciesWithCounter.keySet())
		{
			if(pharmaciesWithCounter.get(id) == medications.getMedications().size())
			{
				Double price = pharmaciesWithPrice.get(id);
				Pharmacy pharmacy = pharmacyService.findOne(id);
				pharmacyDTOs.add(new PharmacyWithMedicationsPriceQrDTO(new PharmacyDTO(pharmacy), price));
			}
		}

		return new ResponseEntity<List<PharmacyWithMedicationsPriceQrDTO>>(pharmacyDTOs, HttpStatus.OK);	
	}
	
	
	
	@GetMapping(value = "/checkCanRate")
	public ResponseEntity<Boolean> checkCanRate(
			@RequestParam String patientUsername, @RequestParam Long pharmacyId) {


		PharmacyRating rating = pharmacyRatingService.findOneByPatientAndPharmacy(patientUsername, pharmacyId);
		
		//if a rating already exists, that means the patient can rate the pharmacy
		if (rating != null) {
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		else {
			//checking if any reservation exists in this pharmacy
			List<Reservation> reservations = reservationService.findAllCompletedByPatientAndPharmacy(patientUsername, pharmacyId);
			if (reservations.size() > 0) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
			
			//checking if any appointment exist in this pharmacy
			List<Appointment> appointments = appointmentService.findAllConcludedByPatientAndPharmacy(patientUsername, pharmacyId);
			if (appointments.size() > 0) {
				return new ResponseEntity<Boolean>(true, HttpStatus.OK);
			}
			
			//TODO checking if any ePrescriptions exist in this pharmacy
			
			return new ResponseEntity<Boolean>(false, HttpStatus.OK);	
		}

	}
	
	@GetMapping(value = "/ratePharmacy")
	public ResponseEntity<Double> ratePharmacy(
			@RequestParam String patientUsername, @RequestParam Long pharmacyId, @RequestParam double ratedValue) {

		if (ratedValue < 0 || ratedValue > 5) {
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
 
		}
		Patient patient = patientService.findByUsername(patientUsername);
		Pharmacy pharmacy = pharmacyService.findOne(pharmacyId);
		
		PharmacyRating rating = pharmacyRatingService.findOneByPatientAndPharmacy(patientUsername, pharmacyId);
		
		if (rating == null) {
			//create new rating obj
			rating = new PharmacyRating();
			rating.setDate(new Date());
			rating.setPharmacy(pharmacy);
			rating.setPatient(patient);
			rating.setRating(ratedValue);
			pharmacyRatingService.save(rating);
		}
		else {
			//update existing rating obj
			rating.setRating(ratedValue);
			rating.setDate(new Date());
			pharmacyRatingService.save(rating);		
		}
		
		//updating value in Pharmacy object
		List<PharmacyRating> pharmaciesRatings = pharmacyRatingService.findAllByPharmacy(pharmacyId);
		int numRatings = pharmaciesRatings.size();
		double newRating = 0;
		
		for (PharmacyRating er : pharmaciesRatings) {
			newRating += er.getRating();
		}
		
		newRating /= numRatings;
		
		DecimalFormat df = new DecimalFormat("#.##");
		newRating = Double.parseDouble(df.format(newRating));
		
		pharmacy.setRating(newRating);
		pharmacyService.save(pharmacy);

		return new ResponseEntity<Double>(newRating, HttpStatus.OK);
	}

}
