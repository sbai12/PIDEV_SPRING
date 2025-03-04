package esprit.tn.coursesspace.Service;


import esprit.tn.coursesspace.Entity.CourseModule;
import esprit.tn.coursesspace.Entity.User;
import esprit.tn.coursesspace.Repository.ICoursesModuleRepository;
import esprit.tn.coursesspace.Repository.ICoursesModuleRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

@Slf4j
public class CoursesModuleServiceImpl implements ICoursesModuleService{

    @Autowired
    ICoursesModuleRepository courseModuleRepository;
@Override
   public CourseModule addModule(CourseModule courseModule) {
       return courseModuleRepository.save(courseModule);
   }
@Override
    public List<CourseModule> getAllModules() {
        return courseModuleRepository.findAll();
    }
@Override
    public void deleteModule(Long id) {
        courseModuleRepository.deleteById(id);
    }
  @Override
    public CourseModule updateModule(Long id, CourseModule courseModule) {
        if (courseModuleRepository.existsById(id)) {
            courseModule.setId_module(id);
            return courseModuleRepository.save(courseModule);
        }
        return null;
    }
    @Override
    public List<CourseModule> getAllCoursesModule() {
        return (List<CourseModule>) courseModuleRepository.findAll();
    }

}
