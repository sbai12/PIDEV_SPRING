package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course addCourse(Course course);

    Optional<Course> getCourseById(Long id);

    void deleteCourse(Long id);
    Course updateCourse(Long id, Course course);
    List<Course> getAllCourses();

    Course addCourseWithFile(Course course, MultipartFile file) throws IOException;

    void uploadFile(Long id, MultipartFile file) throws IOException;

    byte[] getFileByCourseId(Long courseId) throws IOException;
}