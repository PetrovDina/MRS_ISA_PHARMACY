package mrsisa12.pharmacy;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import mrsisa12.pharmacy.service.PatientService;

@SpringBootApplication
public class App 

	
{
	
	@Autowired
	private PatientService patientService;
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    	

    	
        System.out.println( "Hello World!" );
    }
}
