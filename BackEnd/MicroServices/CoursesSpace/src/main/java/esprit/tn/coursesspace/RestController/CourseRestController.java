package esprit.tn.coursesspace.RestController;

import esprit.tn.coursesspace.Entity.Course;
import esprit.tn.coursesspace.Service.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @PostMapping("/add-course-with-file")
    public ResponseEntity<String> addCourseWithFile(@RequestParam("file") MultipartFile file,
                                                    @RequestParam("title") String title,
                                                    @RequestParam("description") String description,
                                                    @RequestParam("duration") String duration,
                                                    @RequestParam("contentType") String contentType) {
        try {
            Course course = new Course(title, description, Integer.parseInt(duration), contentType);
            course.setContentFile(file.getBytes());
            courseServiceImpl.addCourse(course);
            return ResponseEntity.ok("Cours ajouté avec succès!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du cours");
        }
    }

    @GetMapping("/get-Course/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseServiceImpl.getCourseById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseServiceImpl.deleteCourse(id);
    }

    @PutMapping("/update/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseServiceImpl.updateCourse(id, course);
    }

    @GetMapping("/getall-courses")
    public List<Course> getAllCourses() {
        return courseServiceImpl.getAllCourses();
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getCourseFile(@PathVariable Long id) {
        try {
            byte[] fileData = courseServiceImpl.getFileByCourseId(id);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileData);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
