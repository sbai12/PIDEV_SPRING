package esprit.tn.coursesspace.Repository;

import esprit.tn.coursesspace.DTO.CourseDTO;
import esprit.tn.coursesspace.Entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT new esprit.tn.coursesspace.DTO.CourseDTO(c.idCourse, c.title, c.description, c.contentType, c.duration, c.fileName, c.fileData) " +
            "FROM Course c")
    List<CourseDTO> getAllCoursesDTO();
}