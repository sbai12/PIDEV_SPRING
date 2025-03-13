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
    private String description;
    private int duration;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @Enumerated(EnumType.STRING)
    ContentType contentType;
    private String fileName;


    @ManyToOne
    @JoinColumn(name = "id_module")
    CourseModule courseModule;


    @OneToMany(mappedBy = "course")
    Set<Quiz> quizzes;

    @ManyToMany(mappedBy = "teacherCourses")
    Set<User> teachers;

    @ManyToMany(mappedBy = "courses")
    Set<User> candidates;

    public Course(String title, String description, int duration, ContentType contentType, byte[] fileData) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.contentType = contentType;
        this.fileData = fileData;
    }
}