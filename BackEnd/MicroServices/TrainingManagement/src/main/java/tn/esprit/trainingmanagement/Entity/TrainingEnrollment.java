package tn.esprit.trainingmanagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
     Student student;

    @ManyToOne
    @JoinColumn(name = "training_id")
     Training training;

    @Enumerated(EnumType.STRING)
    public EnrollmentStatus status;

}
