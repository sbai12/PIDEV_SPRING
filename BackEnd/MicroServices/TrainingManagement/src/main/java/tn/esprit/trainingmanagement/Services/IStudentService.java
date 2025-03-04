package tn.esprit.trainingmanagement.Services;

import tn.esprit.trainingmanagement.Entity.Student;

import java.util.List;


public interface IStudentService {

    Student SaveStudent(Student student);
    List<Student> GetAllStudents();
    Student updateStudent(Long id , Student studentDetails);
    void deleteStudent(Long id);

}
