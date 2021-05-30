package mrsisa12.pharmacy.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.AppointmentDTO;
import mrsisa12.pharmacy.dto.TherapyDTO;
import mrsisa12.pharmacy.dto.TherapyWithItemsDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.Therapy;
import mrsisa12.pharmacy.model.enums.EPrescriptionStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.TherapyService;

@RestController
@RequestMapping("/therapy")
public class TherapyController {
	
	@Autowired
	private TherapyService therapyService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	private Random random = new Random();
    private static final String SOURCES ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
	@GetMapping(value = "/create")
	public ResponseEntity<TherapyDTO> createEPrescription(@RequestParam String appointmentId){
		Appointment appointment = appointmentService.findOneWithPatient(Long.parseLong(appointmentId));
		Therapy ePrescription = new Therapy();
		ePrescription.setPatient(appointment.getPatient());
		ePrescription.setPrescribedDate(new Date());
		ePrescription.setStatus(EPrescriptionStatus.COMPLETED);
		//generates code
		int length = 10;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
		ePrescription.setCode(new String(text));
		
		therapyService.save(ePrescription);
		
		return new ResponseEntity<>( new TherapyDTO(ePrescription) ,HttpStatus.CREATED);
	}

	
	@GetMapping(value = "/byPatient")
	public ResponseEntity<List<TherapyWithItemsDTO>> getAllByPatient(@RequestParam String patientUsername) {

		List<Therapy> prescriptions = therapyService.findAllByPatientWithPrescriptionItems(patientUsername);
		

		// convert to DTOs
		List<TherapyWithItemsDTO> prescriptionsDTO = new ArrayList<>();
		for (Therapy prescription : prescriptions) {
			
			prescriptionsDTO.add(new TherapyWithItemsDTO(prescription));
		}

		return new ResponseEntity<>(prescriptionsDTO, HttpStatus.OK);
	}
}
