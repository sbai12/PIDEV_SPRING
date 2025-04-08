package tn.esprit.trainingmanagement.Services;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Repository.StudentRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailNotificationService implements IEmailNotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private StudentRepo studentRepository;

    @Override
    public void sendCourseNotification(Long courseId, String courseName, String message) {

        // Récupérer tous les étudiants inscrits
        List<Student> students = studentRepository.findAll();

        // Boucle pour envoyer l'email à chaque étudiant
        for (Student student : students) {
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(student.getEmail());  // Email de l'étudiant
            email.setSubject("Nouvelle Notification de Formation");
            email.setText("Bonjour " + student.getFirstName() + ",\n\n" +
                    message + "\n\nFormation : " + courseName + "\n\nCordialement, L'équipe.");
            mailSender.send(email);
        }
    }
}
