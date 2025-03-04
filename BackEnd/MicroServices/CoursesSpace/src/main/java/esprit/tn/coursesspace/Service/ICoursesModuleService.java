package esprit.tn.coursesspace.Service;

import esprit.tn.coursesspace.Entity.CourseModule;
import esprit.tn.coursesspace.Entity.User;

import java.util.List;

public interface ICoursesModuleService {
    List<CourseModule> getAllModules();
    CourseModule addModule(CourseModule courseModule);
    void deleteModule(Long id);
    CourseModule updateModule(Long id, CourseModule courseModule);
    List<CourseModule> getAllCoursesModule();
}
