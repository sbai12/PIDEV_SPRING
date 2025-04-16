package tn.esprit.trainingmanagement.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.EnrollmentStatus;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.core.NestedExceptionUtils.buildMessage;

@Service
public class MeetingServiceImpl implements IMeetingService{
    private static final Logger logger = LoggerFactory.getLogger(MeetingServiceImpl.class);  // Logger pour les logs

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
        logger.info("Lien de réunion généré pour la formation {} : {}", training.getName(), lien);
        return lien;
    }


    @Override
    public void envoyerInvitations(Long idFormation, LocalDateTime dateSession) {
        Training training = trainingRepository.findById(idFormation)
                .orElseThrow(() -> new RuntimeException("Formation introuvable"));

        // Vérifier si le nombre d'étudiants confirmés atteint la capacité maximale
        long confirmedCount = training.getEnrollments().stream()
                .filter(e -> e.getStatus() == EnrollmentStatus.CONFIRMED)
                .count();

        // Si la capacité maximale est atteinte, envoyer les invitations
        if (confirmedCount >= training.getMaxCapacity()) {
            String lien = training.getMeetingLink();
            if (lien == null || lien.isEmpty()) {
                lien = genererLienReunion(idFormation, dateSession);  // Générer un lien si nécessaire
            }

            String message = buildMessage(training.getName(), dateSession, lien);

            try {
                // Envoi au professeur
                envoyerEmail(training.getAdmin().getEmail(), "Invitation formation : " + training.getName(), message);

                // Envoi aux étudiants confirmés
                training.getEnrollments().stream()
                        .filter(e -> e.getStatus() == EnrollmentStatus.CONFIRMED)
                        .forEach(e -> envoyerEmail(e.getStudent().getEmail(), "Invitation formation : " + training.getName(), message));

                logger.info("Les invitations ont été envoyées pour la formation {}", training.getName());

            } catch (Exception e) {
                logger.error("Erreur lors de l'envoi des invitations pour la formation {} : {}", training.getName(), e.getMessage());
                throw new RuntimeException("Erreur lors de l'envoi des invitations", e);
            }
        } else {
            logger.info("La capacité maximale n'a pas été atteinte pour la formation {}. Aucune invitation envoyée.", training.getName());
        }
    }

    // Construire le message d'invitation
    private String buildMessage(String nomFormation, LocalDateTime date, String lien) {
        return "Bonjour,\n\nVous êtes invité à la formation : " + nomFormation +
                " le " + date.toString() + "\nLien de la réunion : " + lien + "\nÀ bientôt.";
    }

    @Override
    public void envoyerEmail(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);  // Destinataire
        msg.setSubject(subject);  // Sujet
        msg.setText(content);  // Contenu du message

        try {
            mailSender.send(msg);  // Envoi de l'e-mail
            logger.info("E-mail envoyé à : " + to);  // Log pour confirmer l'envoi
        } catch (Exception e) {
            logger.error("Erreur lors de l'envoi de l'e-mail à " + to + ": " + e.getMessage());
            throw new RuntimeException("Erreur lors de l'envoi de l'e-mail", e);
        }
    }
}