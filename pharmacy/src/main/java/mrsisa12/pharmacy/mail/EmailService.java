package mrsisa12.pharmacy.mail;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Employee;
import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Patient;
import mrsisa12.pharmacy.model.Pharmacy;
import mrsisa12.pharmacy.model.PharmacyStorageItem;
import mrsisa12.pharmacy.model.Promotion;
import mrsisa12.pharmacy.model.Supplier;
import mrsisa12.pharmacy.model.SystemAdmin;
import mrsisa12.pharmacy.model.User;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;	
	
	private static final int NUM_OF_QUICK_SERVICE_THREADS = 10;

    private final ScheduledExecutorService quickService = Executors.newScheduledThreadPool(NUM_OF_QUICK_SERVICE_THREADS);

    public void sendEmail(EmailContent content){
    	String[] emailIds = new String[content.getRecipients().size()];
    	for(int i = 0; i <content.getRecipients().size(); i++) {
    		emailIds[i] = content.getRecipients().get(i);
    	}
    	
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(emailIds);
        email.setSubject(content.getSubject());
        email.setText(content.getBody());
        quickService.submit(() -> javaMailSender.send(email));
    }
    
    public void sendEmailToSupplier(Supplier supplier, double price, Order orderWithOrderItems, String kind) {
    	String emailBody = "Dear " + supplier.getFirstName() + " " + supplier.getLastName()
				+ ",\n\nYour offer of " + String.format("%,.2f", price) + " DIN to order with content:\n\n";
		for (OrderItem orderItem : orderWithOrderItems.getOrderItems()) {
			emailBody += " - Medication: \"" + orderItem.getMedication().getName() + "\" with quantity: " + orderItem.getQuantity() + "\n";
		}
		emailBody += "\nhas been " + kind + ".\n\nThank you very much,\nyour " + orderWithOrderItems.getPharmacy().getName() + "!";
		emailBody += "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
		
		// slanje mail-a
		EmailContent email = new EmailContent("Your offer has been " + kind, emailBody);
		email.addRecipient(supplier.getEmail());
		this.sendEmail(email);
    }
    
    public void sendEmailToPatientComplaintEmployeeResponse(Patient patient, SystemAdmin admin, Employee employee)
    {
		String emailBody = "Dear " + patient.getFirstName() + " " + patient.getLastName()
				+ ",\n\nWe want inform you that our administrator " + admin.getUsername() + " responded to your complaint about our "
				+ "employee " + employee.getFirstName() + " " + employee.getLastName() + "."
				+ "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
		
		EmailContent email = new EmailContent("Employee complaint response notification", emailBody);
		email.addRecipient(patient.getEmail());
        this.sendEmail(email);
    }
    
    public void sendEmailToPatientComplaintPharmacyResponse(Patient patient, SystemAdmin admin, Pharmacy pharmacy)
    {
		String emailBody = "Dear " + patient.getFirstName() + " " + patient.getLastName()
				+ ",\n\nWe want inform you that our administrator " + admin.getUsername() + " responded to your complaint about "
				+ "pharmacy " + pharmacy.getName() + "."
				+ "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
		
		EmailContent email = new EmailContent("Pharmacy complaint response notification", emailBody);
		email.addRecipient(patient.getEmail());
        this.sendEmail(email);
    }
    
    public void confirmationEmailUserRegistration(User user)
    {
    	String emailBody = "Dear " + user.getFirstName() + " " + user.getLastName()
		+ ",\n\nTo verify registration on our website click on following link: "
		+ "http://localhost:8080/auth/confirm-registration/" + user.getUsername() 
		+ "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
    	
    	EmailContent email = new EmailContent("Registration confirmation", emailBody);
		email.addRecipient(user.getEmail());
        this.sendEmail(email);
    }

	public void sendDiscountMailToPatient(Patient patient, Promotion promotion) {
		String emailBody = "Dear " + patient.getFirstName() + " " + patient.getLastName()
				+ ",\n\nWe have special discount for you! Just due " + promotion.getDueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +".\n\n";
		for (PharmacyStorageItem psi : promotion.getPharmacyStorageItems()) {
			emailBody += " \t\"" + psi.getMedication().getName() + "\" is on sale!\n";
		}
		emailBody += "\nVisit our website and check it out!";
		emailBody += "\n\nThank you very much,\nyour " + promotion.getPharmacyStorageItems().get(0).getPharmacy().getName() + "!";
		emailBody += "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
		
		// slanje mail-a
		EmailContent email = new EmailContent("Special discount due " + promotion.getDueDate().toString(), emailBody);
		email.addRecipient(patient.getEmail());
		this.sendEmail(email);
		
	}
    
}
