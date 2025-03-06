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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;

    private String title;
    private String category;
    private int duration;
    private String description;

    @Lob
    private byte[] contentFile;

    private String filePath;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(name = "course_module_id")
    private CourseModule courseModule;

    @OneToMany(mappedBy = "course")
    private Set<Quiz> quizzes;

    @ManyToMany(mappedBy = "teacherCourses")
    private Set<User> teachers;

    @ManyToMany(mappedBy = "courses") // Correspond exactement au nom dans User
    private Set<User> candidates;


    public Course(String title, String description, int duration, String contentType) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.contentType = contentType != null ? ContentType.valueOf(contentType) : null;
    }

    public void setContentFile(byte[] contentFile) {
        this.contentFile = contentFile;
    }
}
