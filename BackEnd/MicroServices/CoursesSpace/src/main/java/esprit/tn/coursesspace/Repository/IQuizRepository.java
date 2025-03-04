package esprit.tn.coursesspace.Repository;


import esprit.tn.coursesspace.Entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, Long> {
}
