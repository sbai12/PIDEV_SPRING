package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;

import java.util.List;
import java.util.Optional;

public interface ICourseService {
    Course addCourse(Course course);

    Optional<Course> getCourseById(Long id);

    void deleteCourse(Long id);
    Course updateCourse(Long id, Course course);
    List<Course> getAllCourses();
}