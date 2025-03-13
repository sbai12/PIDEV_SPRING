package esprit.tn.coursesspace.Service;

import esprit.tn.coursesspace.Entity.CourseFile;
import esprit.tn.coursesspace.Repository.ICourseFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import esprit.tn.coursesspace.Entity.CourseFile;

import java.util.List;

@Service
public class FileServiceImpl {

    private final ICourseFileRepository courseFileRepository;

    @Autowired
    public FileServiceImpl(ICourseFileRepository courseFileRepository) {
        this.courseFileRepository = courseFileRepository;
    }

    public List<CourseFile> getCourseFilesByidCourse(Long idCourse) {
        return courseFileRepository.findByidCourse(idCourse);
    }
}

