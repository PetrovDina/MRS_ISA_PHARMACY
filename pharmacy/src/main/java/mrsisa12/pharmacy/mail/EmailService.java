package mrsisa12.pharmacy.mail;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import mrsisa12.pharmacy.model.Order;
import mrsisa12.pharmacy.model.OrderItem;
import mrsisa12.pharmacy.model.Supplier;

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
    	String emailBody = "Hello " + supplier.getFirstName() + " " + supplier.getLastName()
				+ ",\n\nYour offer of " + String.format("%,.2f", price) + " DIN to order with content:\n\n";
		for (OrderItem orderItem : orderWithOrderItems.getOrderItems()) {
			emailBody += " - Medication: \"" + orderItem.getMedication().getName() + "\" with quantity: " + orderItem.getQuantity() + "\n";
		}
		emailBody += "\nhas been " + kind + ".\n\nThank you very much,\nyour " + orderWithOrderItems.getPharmacy().getName();
		emailBody += "\n\nThis is an automatically generated email – please do not reply to it. ©Tim12-MRS-ISA";
		
		// slanje mail-a
		EmailContent email = new EmailContent("Your offer has been " + kind, emailBody);
		email.addRecipient(supplier.getEmail());
		this.sendEmail(email);
    }
}
