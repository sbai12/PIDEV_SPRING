package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TrainingEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
     Student student;

    @ManyToOne
    @JoinColumn(name = "training_id")
     Training training;

    @Enumerated(EnumType.STRING)
     EnrollmentStatus status = EnrollmentStatus.PENDING; // Par défaut, l'inscription est en attente


}
