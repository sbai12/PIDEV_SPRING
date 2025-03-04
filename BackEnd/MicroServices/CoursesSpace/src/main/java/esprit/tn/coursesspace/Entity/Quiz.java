package esprit.tn.coursesspace.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id_quiz;
     String title;
     String answer;
     int result;

    @Enumerated(EnumType.STRING)
     QuestionType questionType;

    @Enumerated(EnumType.STRING)
     QuizType quizType;

    @ManyToOne
     Course course;

}
