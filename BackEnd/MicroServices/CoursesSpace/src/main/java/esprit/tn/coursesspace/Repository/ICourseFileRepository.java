package esprit.tn.coursesspace.Repository;

import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.CourseFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICourseFileRepository extends JpaRepository<CourseFile, Long> {
    @Query("SELECT cf FROM CourseFile cf WHERE cf.course.idCourse = :idCourse")
    List<CourseFile> findByidCourse(@Param("idCourse") Long idCourse);
    List<CourseFile> findByCourse(Course course);
}
