package esprit.tn.coursesspace.RestController;


import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Service.CourseServiceImpl;
import esprit.tn.coursesspace.Service.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:4200")
public class CourseRestController {

    @Autowired
    private CourseServiceImpl courseServiceImpl;
    @PostMapping("/add-course")
    public Course addCourse(@RequestBody Course course) {
        return courseServiceImpl.addCourse(course);
    }
    @GetMapping("/get-Course/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseServiceImpl.getCourseById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseServiceImpl.deleteCourse(id);
    }
    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseServiceImpl.updateCourse(id, course);
    }
    @GetMapping("/getall-User")
    List<Course> getAllCourses() {
        return courseServiceImpl.getAllCourses();
    }

}
