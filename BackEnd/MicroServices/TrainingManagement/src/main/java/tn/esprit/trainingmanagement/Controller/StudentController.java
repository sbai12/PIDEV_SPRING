package tn.esprit.trainingmanagement.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Services.StudentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;
    @PostMapping("/register")
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

}
