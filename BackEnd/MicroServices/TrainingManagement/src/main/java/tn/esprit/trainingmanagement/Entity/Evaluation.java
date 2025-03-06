package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant de l'évaluation

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; // Étudiant qui a fait l'évaluation

    @ManyToOne
    @JoinColumn(name = "training_id")
    private Training training; // Formation évaluée

    private int nombreEtoiles; // Nombre d'étoiles attribuées (1 à 5)

    private String commentaire; // Commentaire optionnel
}
