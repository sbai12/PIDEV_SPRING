package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Professor extends User {
    String specialization;

    @OneToMany(mappedBy = "professor")
    List<Training> trainings;
}


