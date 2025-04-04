package tn.esprit.trainingmanagement.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.EnrollmentStatus;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MeetingServiceImpl implements IMeetingService{

    @Autowired
    TrainingRepo trainingRepository;

    @Autowired
    JavaMailSender mailSender;

    @Override
    public String genererLienReunion(Long idFormation, LocalDateTime dateSession) {
        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));

        String lien = "https://meet.fakeplatform.com/" + UUID.randomUUID();
        training.setMeetingLink(lien);
        training.setDateSession(dateSession);
        trainingRepository.save(training);
        return lien;
    }

    @Override
    public void envoyerInvitations(Long idFormation, LocalDateTime dateSession) {

        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));

        String lien = training.getMeetingLink();
        if (lien == null || lien.isEmpty()) {
            lien = genererLienReunion(idFormation, dateSession);
        }

        String message = buildMessage(training.getName(), dateSession, lien);

        // Envoi au professeur
        envoyerEmail(training.getAdmin().getEmail(), "Invitation formation : " + training.getName(), message);

        // Envoi aux étudiants confirmés
        training.getEnrollments().stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.CONFIRMED)
                .forEach(e -> envoyerEmail(e.getStudent().getEmail(), "Invitation formation : " + training.getName(), message));
    }

    private String buildMessage(String nomFormation, LocalDateTime date, String lien) {
        return "Bonjour,\n\nVous êtes invité à la formation : " + nomFormation +
                " le " + date.toString() + "\nLien de la réunion : " + lien + "\nÀ bientôt.";
    }

    private void envoyerEmail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);
        mailSender.send(msg);
    }
}

