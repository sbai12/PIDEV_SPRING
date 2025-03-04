package esprit.tn.coursesspace.Service;



import esprit.tn.coursesspace.Entity.Quiz;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Repository.ICourseRepository;
import esprit.tn.coursesspace.Repository.IQuizRepository;
import esprit.tn.coursesspace.Repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.desktop.QuitEvent;
import java.util.List;
@Service
@AllArgsConstructor

@Slf4j
public class QuizServiceImpl implements IQuizService{
    private IQuizRepository quizRepository;


    private ICourseRepository courseRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuiz() {
        return (List<Quiz>) quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(long quiz_id) {
        return quizRepository.findById(quiz_id).orElse(null);
    }

    @Override
    public void deleteQuiz(long quiz_id) {
        quizRepository.deleteById(quiz_id);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.findById(quiz.getId_quiz())
                .map(existingQuiz -> {
                    existingQuiz.setAnswer(
                            quiz.getTitle());
                    existingQuiz.setCourse(quiz.getCourse());
                    existingQuiz.setQuestionType(quiz.getQuestionType());
                    existingQuiz.setQuizType(quiz.getQuizType());
                    existingQuiz.setResult(quiz.getResult());
                    return quizRepository.save(existingQuiz);
                })
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + quiz.getId_quiz()));

    }
}
