package esprit.tn.coursesspace.Entity;

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
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id_user;
    String username;
    String email;
    String password;
    @Enumerated(EnumType.STRING)
    Role role;

    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "idCourse")
    )
    private Set<Course> teacherCourses;  // Renommé pour éviter toute confusion

    @ManyToMany
    @JoinTable(
            name = "course_candidate",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "idCourse")
    )
    private Set<Course> courses; // Garde ce nom, c'est ce qui cause l'erreur actuelle

}