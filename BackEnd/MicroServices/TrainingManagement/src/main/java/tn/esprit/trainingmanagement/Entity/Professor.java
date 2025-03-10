package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Professor extends User {
    String specialization;

    @OneToMany(mappedBy = "professor" , cascade = CascadeType.ALL, orphanRemoval = true)
    List<Training> trainings;
}


