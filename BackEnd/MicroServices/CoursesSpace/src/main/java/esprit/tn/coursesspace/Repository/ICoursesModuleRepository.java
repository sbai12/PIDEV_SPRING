package esprit.tn.coursesspace.Repository;

import esprit.tn.coursesspace.Entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICoursesModuleRepository extends JpaRepository<CourseModule, Long> {

}
