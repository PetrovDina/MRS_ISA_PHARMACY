package mrsisa12.pharmacy.controller;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import mrsisa12.pharmacy.dto.EPrescriptionDTO;
import mrsisa12.pharmacy.model.Appointment;
import mrsisa12.pharmacy.model.EPrescription;
import mrsisa12.pharmacy.model.enums.EPrescriptionStatus;
import mrsisa12.pharmacy.service.AppointmentService;
import mrsisa12.pharmacy.service.EPrescriptionService;

@RestController
@RequestMapping("/eprescription")
public class EPrescriptionController {
	
	@Autowired
	private EPrescriptionService ePrescriptionService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	private Random random = new Random();
    private static final String SOURCES ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
	
	@GetMapping(value = "/create")
	public ResponseEntity<EPrescriptionDTO> createEPrescription(@RequestParam String appointmentId){
		Appointment appointment = appointmentService.findOneWithPatient(Long.parseLong(appointmentId));
		EPrescription ePrescription = new EPrescription();
		ePrescription.setPatient(appointment.getPatient());
		ePrescription.setPrescribedDate(new Date());
		ePrescription.setStatus(EPrescriptionStatus.CREATED);
		//generates code
		int length = 10;
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = SOURCES.charAt(random.nextInt(SOURCES.length()));
        }
		ePrescription.setCode(new String(text));
		
		ePrescriptionService.save(ePrescription);
		
		return new ResponseEntity<>( new EPrescriptionDTO(ePrescription) ,HttpStatus.CREATED);
	}

}
