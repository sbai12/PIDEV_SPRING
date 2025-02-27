package tn.esprit.trainingmanagement.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long course_id;
    String title;
    String category;
    int duration;
}
