package tn.esprit.trainingmanagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.trainingmanagement.Services.IMeetingService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/meeting")
public class MeetingController {
    @Autowired
    IMeetingService meetingService;
    @PostMapping("/generate-link")
    public ResponseEntity<String> generateLink(@RequestParam Long idFormation,
                                               @RequestParam String date) {
        // Formater la date manuellement si besoin
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateSession = LocalDateTime.parse(date, formatter);
        String lien = meetingService.genererLienReunion(idFormation, dateSession);
        return ResponseEntity.ok(lien);
    }


    @PostMapping("/send-invitations")
    public ResponseEntity<Void> sendInvitations(@RequestParam Long idFormation,
                                                @RequestParam String date) {
        // Nettoyage de la chaîne de date
        date = date.trim();  // Retirer les espaces superflus

        // Utiliser un DateTimeFormatter pour assurer que la conversion fonctionne
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateSession;
        try {
            dateSession = LocalDateTime.parse(date, formatter);  // Conversion manuelle
        } catch (Exception e) {
            System.out.println("Erreur de conversion de la date : " + e.getMessage());
            return ResponseEntity.status(400).body(null);  // Retourner une réponse d'erreur appropriée
        }

        System.out.println("idFormation: " + idFormation);
        System.out.println("Date: " + dateSession);

        try {
            // Appel au service pour envoyer les invitations
            meetingService.envoyerInvitations(idFormation, dateSession);
            System.out.println("Les invitations ont été envoyées.");
            return ResponseEntity.ok().build();  // Retourner 200 OK si tout fonctionne
        } catch (Exception e) {
            System.out.println("Erreur lors de l'envoi des invitations : " + e.getMessage());
            return ResponseEntity.status(500).body(null);  // Retourner une réponse 500 si une erreur survient
        }
    }}
