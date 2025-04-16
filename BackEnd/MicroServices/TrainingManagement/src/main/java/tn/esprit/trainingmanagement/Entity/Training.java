package tn.esprit.trainingmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@JsonIgnoreProperties({"enrollments"})  // Ignorer la sérialisation des enrollments pour éviter la boucle infinie

public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idForm;
    String name;
    String description;
    int duration;
    int maxCapacity;
     String status;
     LocalDateTime dateSession;


    @ManyToOne
    @JoinColumn(name = "professor_id")
    @JsonBackReference
    Admin admin;


    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL , orphanRemoval = true)
     List<TrainingEnrollment> enrollments;

     String meetingLink; //pour le lien de réunion

}
