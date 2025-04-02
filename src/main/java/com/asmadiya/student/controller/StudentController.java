package com.asmadiya.student.controller;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // Fetch all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get the top student in a particular state
    @GetMapping("/top/{state}")
    public Student getTopStudentInState(@PathVariable String state) {
        return studentService.getTopPercentStudentInState(state);
    }

    // Get the topper across all states
    @GetMapping("/topper")
    public Student getTopperInAllStates() {
        return studentService.getTopperInAllStates();
    }
}
