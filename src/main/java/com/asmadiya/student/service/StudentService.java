package com.asmadiya.student.service;

import com.asmadiya.student.entity.Student;
import java.util.List;

public interface StudentService {
    List<Student> getAllStudents();
    Student saveStudent(Student student);
    Student getTopPercentStudentInState(String state);
    Student getTopperInAllStates();
}
