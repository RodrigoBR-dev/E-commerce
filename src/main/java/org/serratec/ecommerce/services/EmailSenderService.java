package org.serratec.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailSenderService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	public void sendSimpleMessage(String destinatario, String assunto, String texto) {
		SimpleMailMessage mensagem = new SimpleMailMessage();
		mensagem.setFrom("grupo6apirest@gmail.com");
		mensagem.setTo(destinatario);
		mensagem.setSubject(assunto);
		mensagem.setText(texto);
		mailSender.send(mensagem);
	}
}
