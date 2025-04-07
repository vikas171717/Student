package com.asmadiya.student.service;

import java.util.List;
import com.asmadiya.student.entity.Student;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student getTopPercentStudentInState(String state);
    Student getTopperInAllStates();

}
