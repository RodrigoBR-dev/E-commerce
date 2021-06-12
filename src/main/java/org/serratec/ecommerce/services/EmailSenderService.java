package org.serratec.ecommerce.services;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@Configuration
public class EmailSenderService {
	
	@Autowired
	JavaMailSender javaMail;
	
	public void sendSimpleMessage(String destinatario, String assunto, String texto) throws MessagingException {
		var mail = javaMail.createMimeMessage();
		mail.setSubject(assunto);
		var helper = new MimeMessageHelper(mail, true);
		helper.setFrom("grupo6apirest@gmail.com");
		helper.setTo(destinatario);
		helper.setText(texto,true);
		javaMail.send(mail);
	}
}
