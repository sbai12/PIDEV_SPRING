package esprit.tn.coursesspace.Entity;

import esprit.tn.coursesspace.Service.CoursesModuleServiceImpl;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id_course;
     String title;
     String category;
     int duration;
     String description;
    @Enumerated(EnumType.STRING)
     ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "id_module")
    CourseModule coursemodule;

    @OneToMany(mappedBy = "course")
    Set<Quiz> quizzes;

    @ManyToMany(mappedBy = "CourseSet")
   Set<User> teachers;

    @ManyToMany(mappedBy = "courses")
    Set<User> candidates;
}