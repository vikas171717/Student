package com.asmadiya.student.controller;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.serviceImpl.StudentServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    // Fetch all students
    @GetMapping("/get-all-students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get the top student in a particular state
    @GetMapping("/top/{state}")
    public ResponseEntity<Student> getTopStudentInState(@PathVariable String state) {
        Student student = studentService.getTopPercentStudentInState(state) ;
        return ResponseEntity.ok(student) ;
    }

    // Get the topper across all states
    @GetMapping("/topper")
    public Student getTopperInAllStates() {
        return studentService.getTopperInAllStates();
    }
}
