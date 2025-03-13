package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.DTO.CourseDTO;
import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.CourseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICourseService {
    Course addCourse(Course course);

    byte[] genererPdfCours(Long idCours);



    Optional<Course> getCourseById(Long id);

    void deleteCourse(Long id);
    Course updateCourse(Long id, Course course);
    List<CourseDTO> getAllCourses();

    Course addCourseWithFile(Course course, MultipartFile file) throws IOException;

    void uploadFile(Long id, MultipartFile file) throws IOException;

    byte[] getFileByCourseId(Long courseId) throws IOException;

    Course assignCourseToModule(Long courseId, Long moduleId);
    Course assignTeacherToCourse(Long courseId, Long userId);

    Map<String, String> saveFile(Long idCourse, MultipartFile file) throws IOException;

    List<CourseFile> getFilesForCourse(Long courseId);
}