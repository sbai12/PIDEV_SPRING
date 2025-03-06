package esprit.tn.coursesspace.Service;

import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Repository.ICourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;

    @Override
    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            course.setIdCourse(id);
            return courseRepository.save(course);
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    @Override
    public Course addCourseWithFile(Course course, MultipartFile file) throws IOException {
        byte[] fileBytes = file.getBytes();
        course.setContentFile(fileBytes);
        return courseRepository.save(course);
    }

    @Override
    public void uploadFile(Long id, MultipartFile file) throws IOException {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isPresent()) {
            Course course = courseOpt.get();
            course.setContentFile(file.getBytes());
            courseRepository.save(course);
        } else {
            throw new EntityNotFoundException("Course not found with id " + id);
        }
    }
    @Override
    public byte[] getFileByCourseId(Long courseId) throws IOException {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec l'ID: " + courseId));

        if (course.getContentFile() == null) {
            throw new RuntimeException("Aucun fichier trouvé pour ce cours");
        }

        return course.getContentFile();  // Utilise contentFile ici
    }


}
