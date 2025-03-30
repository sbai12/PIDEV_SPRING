package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idForm;
    String name;
    String description;
    int duration;
    int maxCapacity;
     String status;


    @ManyToOne
    @JoinColumn(name = "professor_id")
    Professor professor;


    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL , orphanRemoval = true)
     List<TrainingEnrollment> enrollments;

     String meetingLink; //pour le lien de réunion

}
