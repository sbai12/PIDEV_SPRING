package tn.esprit.trainingmanagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender; // Injecté automatiquement par Spring

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rihem.bousbih1@gmail.com"); // Adresse expéditeur (à configurer)
        message.setTo(to); // Destinataire dynamique
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
        System.out.println("Email envoyé avec succès à " + to);
    }
}