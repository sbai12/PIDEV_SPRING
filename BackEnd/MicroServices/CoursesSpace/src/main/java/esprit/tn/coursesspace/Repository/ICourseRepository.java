package esprit.tn.coursesspace.Repository;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

}
