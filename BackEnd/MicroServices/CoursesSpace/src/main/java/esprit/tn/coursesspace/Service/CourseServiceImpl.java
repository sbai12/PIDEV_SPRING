package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Repository.ICourseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor


public class CourseServiceImpl implements ICourseService {
    @Autowired
    ICourseRepository courseRepository;

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
            course.setId_course(id);
            return courseRepository.save(course);
        }
        return null;
    }
    @Override
    public List<Course> getAllCourses() {
        return (List<Course>) courseRepository.findAll();
    }

}