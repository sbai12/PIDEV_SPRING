package tn.esprit.trainingmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDate date;
    String time;
    String location;

    @ManyToOne
    Course course;

    @ManyToMany
    List<Student> students;

    @ManyToOne
    Professor professor;
}


