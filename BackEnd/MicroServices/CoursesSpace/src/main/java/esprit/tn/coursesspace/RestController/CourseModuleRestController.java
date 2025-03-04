package esprit.tn.coursesspace.RestController;

import esprit.tn.coursesspace.Entity.CourseModule;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Repository.ICoursesModuleRepository;
import esprit.tn.coursesspace.Service.CoursesModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coursemodules")
public class CourseModuleRestController {

    @Autowired
    CoursesModuleServiceImpl coursesModuleServiceImpl;

    @DeleteMapping("/{id}")
    public void deleteModule(@PathVariable Long id) {
        coursesModuleServiceImpl.deleteModule(id);
    }
    @PostMapping("/add")
    public CourseModule addModule(@RequestBody CourseModule courseModule) {
        return coursesModuleServiceImpl.addModule(courseModule);
    }
    @GetMapping
    public List<CourseModule> getAllModules() {
        return coursesModuleServiceImpl.getAllModules();
    }

    @PutMapping("/{id}")
    public CourseModule updateModule(@PathVariable Long id, @RequestBody CourseModule courseModule) {
        return coursesModuleServiceImpl.updateModule(id, courseModule);
    }
    @GetMapping("/getall-User")
    List<CourseModule> getAllCoursesModule() {
        return coursesModuleServiceImpl.getAllCoursesModule();
    }

}
