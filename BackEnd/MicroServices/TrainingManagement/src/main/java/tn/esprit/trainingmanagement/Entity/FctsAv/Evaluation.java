package tn.esprit.trainingmanagement.Entity.FctsAv;


import jakarta.persistence.*;
import lombok.Data;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;

import java.time.LocalDateTime;

@Data
@Entity
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "idEtudiant", nullable = false)
    public Student student;

    @ManyToOne
    @JoinColumn(name = "idFormation", nullable = false)
    public Training training; // Référence à l'entité Training

    public int nombreEtoiles;
    public String commentaire;
    public LocalDateTime dateEvaluation;
}
