package esprit.tn.coursesspace.Service;



import esprit.tn.coursesspace.Entity.Quiz;
import esprit.tn.coursesspace.Entity.User;

import java.util.List;

public interface IQuizService {
    Quiz addQuiz(Quiz quiz);
    List<Quiz> getAllQuiz();
    Quiz getQuizById(long quiz_id);
    void deleteQuiz(long quiz_id);

    Quiz updateQuiz(Quiz quiz);

}
