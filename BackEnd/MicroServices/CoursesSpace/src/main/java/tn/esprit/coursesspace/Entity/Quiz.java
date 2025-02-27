package tn.esprit.coursesspace.Entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long quizId;
     String title;
     String answer;
     int result;

    @ManyToOne
    @JoinColumn(name = "course_id")
     Course course;

    @Enumerated(EnumType.STRING)
     QuestionType questionType;

    @Enumerated(EnumType.STRING)
     TypeQuiz typeQuiz;
}

