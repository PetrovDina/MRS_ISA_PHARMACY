package mrsisa12.pharmacy.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;	

    public void sendEmail(EmailContent content){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(content.getRecipient());
        email.setSubject(content.getSubject());
        email.setText(content.getBody());
        javaMailSender.send(email);
    }
}
