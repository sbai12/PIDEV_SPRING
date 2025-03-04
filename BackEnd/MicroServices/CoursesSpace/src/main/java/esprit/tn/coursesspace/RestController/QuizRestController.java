package esprit.tn.coursesspace.RestController;
import esprit.tn.coursesspace.Entity.Quiz;
import esprit.tn.coursesspace.Service.QuizServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
@RestController
@RequestMapping("/quiz")
@AllArgsConstructor

@Slf4j
public class QuizRestController {
    QuizServiceImpl quizService;


    @PostMapping("/add-Quiz")
    Quiz addQuiz(@RequestBody Quiz quiz) {
        return quizService.addQuiz(quiz);
    }

    @GetMapping("/getall-Quiz")
    List<Quiz> getAllQuiz() {
        return quizService.getAllQuiz();
    }

    @GetMapping("get-Quiz/{idQuiz}")
    Quiz getQuizById (@PathVariable("idQuiz") long idQuiz) {
        return quizService.getQuizById(idQuiz);
    }

    @DeleteMapping("/delete-Quiz/{idQuiz}")
    void deleteQuiz(@PathVariable("idquiz") long idQuiz) {
        quizService.deleteQuiz(idQuiz);
    }

    @PutMapping("/update-Quiz")
    public ResponseEntity<?> updateQuiz(@RequestBody Quiz quiz) {
        if (quiz.getId_quiz() == null) {
            return ResponseEntity.badRequest().body("L'ID de Quiz est obligatoire pour la mise à jour.");
        }

        try {
            Quiz updateQuiz = quizService.updateQuiz(quiz);
            return ResponseEntity.ok(updateQuiz);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
