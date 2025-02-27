package tn.esprit.coursesspace.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long moduleId;
     String title;

    @ManyToOne
    @JoinColumn(name = "course_id")
     Course course;
}

