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
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id_module;
     String moduleTitle;

    @OneToMany(mappedBy = "coursemodule", cascade = CascadeType.ALL)
    Set<Course> moduleCourses;

}
