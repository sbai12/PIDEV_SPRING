package tn.esprit.trainingmanagement.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.trainingmanagement.Entity.Student;
import tn.esprit.trainingmanagement.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;


@Service
@AllArgsConstructor
public class StudentServiceImpl implements IStudentService{
    StudentRepo studentRepo;

    @Override
    public Student SaveStudent(Student student) {
        return studentRepo.save(student);
    }

    @Override
    public List<Student> GetAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public Student updateStudent(Long id, Student studentDetails) {
        return studentRepo.findById(id).map(student -> {
            student.setName(studentDetails.getName());
            student.setEmail(studentDetails.getEmail());
            student.setPassword(studentDetails.getPassword());
            student.setTestResult(studentDetails.getTestResult());
            return studentRepo.save(student);
        }).orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepo.deleteById(id);
    }

    }
