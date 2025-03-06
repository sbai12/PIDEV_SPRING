package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // La valeur de l'ID est gérée par la base de données
     Long id;
    String name;
    String description;
    int duration;
    int maxCapacity;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    Professor professor;

    @ManyToMany

    @JoinTable(
            name = "training_student",
            joinColumns = @JoinColumn(name = "training_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    List<Student> enrolledStudents;

    private String meetingLink; //pour le lien de réunion

}
