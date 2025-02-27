package tn.esprit.coursesspace.Entity;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long courseId;
     String title;
     String category;
     int duration;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
     Teacher teacher;

    @Enumerated(EnumType.STRING)
     ContentType contentType;

    @OneToMany(mappedBy = "course")
    Set<Module> modules;
}

