package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Entity.Training;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import tn.esprit.trainingmanagement.Repository.TrainingRepo;
import tn.esprit.trainingmanagement.Services.StudentServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;

    @Autowired
     StudentRepo studentRepository;
    @Autowired
    private TrainingRepo trainingRepo;

    @PostMapping("/registerr")
    public Student SaveStudent(@RequestBody Student student) {
        return studentService.SaveStudent(student);
    }

    @GetMapping
    public List<Student>  GetAllStudents() {
        return studentService.GetAllStudents();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id , @RequestBody Student studentDetails){
        return studentService.updateStudent(id, studentDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping("/register")
    public Student registerStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }




    @GetMapping("/{id}/trainings")
    public Map<String, Object> getStudentTrainings(@PathVariable Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        if (student == null) return Map.of("error", "Student not found");

        List<Training> allTrainings = trainingRepo.findAll();
        List<Training> enrolled = student.getEnrollments().stream()
                .map(enrollment -> enrollment.getTraining())
                .toList();

        List<Training> notEnrolled = allTrainings.stream()
                .filter(t -> !enrolled.contains(t))
                .toList();

        return Map.of(
                "enrolledTrainings", enrolled,
                "otherTrainings", notEnrolled
        );
    }

}
