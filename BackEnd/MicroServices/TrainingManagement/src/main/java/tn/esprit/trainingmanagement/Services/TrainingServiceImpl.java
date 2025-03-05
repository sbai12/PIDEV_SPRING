package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
@AllArgsConstructor

public class TrainingServiceImpl implements ITrainingService{

    @Autowired
    TrainingRepo trainingRepo;
    @Autowired
    EmailService emailService;

    @Autowired
    StudentRepo studentRepo;

    @Override
    public Training SaveTraining(Training training) {
        // Vérifier si la formation existe déjà par son nom
        if (trainingRepo.existsByName(training.getName())) {
            throw new RuntimeException("Formation existe déjà.");
        }
        return trainingRepo.save(training);
    }

    // Méthode pour vérifier si la formation existe
    public boolean existsByName(String name) {
        return trainingRepo.existsByName(name);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepo.findAll();
    }

    @Override
    public Training updateTraining(Long id, Training trainingDetails) {
        return trainingRepo.findById(id).map( training -> {
            training.setName(trainingDetails.getName());
            training.setDescription(trainingDetails.getDescription());
            training.setDuration(trainingDetails.getDuration());
            training.setMaxCapacity(trainingDetails.getMaxCapacity());
            return trainingRepo.save(training);

        }).orElseThrow(() -> new RuntimeException("Training not found with ID: " + id));
    }

    @Override
    public void deleteTraining(Long id) {
        trainingRepo.deleteById(id);

    }

    @Override
    public boolean registerStudent(Long trainingId, Long studentId) {
        Training training = trainingRepo.findById(trainingId).orElse(null);  // Trouve la formation par son ID
        Student student = studentRepo.findById(studentId).orElse(null);  // Trouve l'étudiant par son ID

        if (training != null && student != null) {
            // Vérifie si la capacité n'est pas atteinte
            if (training.getEnrolledStudents().size() < training.getMaxCapacity()) {
                training.getEnrolledStudents().add(student);  // Ajoute l'étudiant à la formation
                trainingRepo.save(training);  // Sauvegarde la mise à jour dans la base de données
                return true;  // Inscription réussie
            }
        }
        return false;  // Retourne false si la formation ou l'étudiant est introuvable, ou si la capacité est atteinte
    }

    public String genererLienReunion(Long idForm, LocalDateTime dateSession) {
        Training training = trainingRepo.findById(idForm)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));
        String baseLink = "https://meet.example.com/join?meeting=" + idForm + "-" + System.currentTimeMillis();
        training.setMeetingLink(baseLink);
        trainingRepo.save(training);
        return baseLink;
    }

    public void envoyerInvitations(Long idForm, List<Long> idEtudiants, LocalDateTime dateSession) {
        Training training = trainingRepo.findById(idForm)
                .orElseThrow(() -> new RuntimeException("Formation non trouvée"));

        // Vérifier si la capacité maximale est atteinte
        if (training.getEnrolledStudents().size() < training.getMaxCapacity()) {
            throw new RuntimeException("La capacité maximale n'est pas encore atteinte");
        }

        // Générer le lien de réunion si non existant
        if (training.getMeetingLink() == null) {
            genererLienReunion(idForm, dateSession);
        }

        // Date de la réunion (une semaine plus tard)
        LocalDateTime meetingDate = dateSession.plusWeeks(1);
        String formattedDate = meetingDate.format(DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy 'à' HH:mm"));

        // Envoyer les invitations aux étudiants avec leurs emails réels}
        for (Long studentId : idEtudiants) {
            Student student = studentRepo.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Étudiant non trouvé avec ID : " + studentId));
            String studentEmail = student.getEmail(); // Récupère l'email réel depuis la base
            envoyerEmailInvitation(studentEmail, training.getName(), formattedDate, training.getMeetingLink());
        }

        // Envoyer l'invitation au professeur
        if (training.getProfessor() != null) {
            String professorEmail = training.getProfessor().getEmail(); // À adapter selon ton modèle
            envoyerEmailInvitation(professorEmail, training.getName(), formattedDate, training.getMeetingLink());
        }
    }
    private void envoyerEmailInvitation(String recipientEmail, String trainingName, String meetingDate, String meetingLink) {
        String subject = "Invitation à la session de formation : " + trainingName;
        String body = "Bonjour,\n\n" +
                "La session de formation de cette formation (" + trainingName + ") aura lieu le " + meetingDate + ".\n" +
                "Veuillez rejoindre la réunion via ce lien : " + meetingLink + "\n\n" +
                "Cordialement,\nL'équipe e-learning";
        emailService.sendEmail(recipientEmail, subject, body); // 3 arguments seulement
    }}