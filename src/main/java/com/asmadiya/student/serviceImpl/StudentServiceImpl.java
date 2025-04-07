package com.asmadiya.student.serviceImpl;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.repository.StudentRepository;
import com.asmadiya.student.service.StudentService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{
    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Fetch all students from the database
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Save a new student to the database
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get the top student in a particular state
    public Student getTopPercentStudentInState(String state) {
        List<Student> studentsInState = studentRepository.findByState(state);
        if (studentsInState.isEmpty()) {
            return null; // or throw exception if you want
        }

        // Find the student with the highest percentage in the state
        return studentsInState.stream()
                .max((s1, s2) -> Double.compare(s1.getPercentage(), s2.getPercentage()))
                .orElse(null);
    }

    // Get the top student across all states
    public Student getTopperInAllStates() {
        List<Student> allStudents = studentRepository.findAll();
        if (allStudents.isEmpty()) {
            return null; 
        }

        // Find the student with the highest percentage across all states
        return allStudents.stream()
                .max((s1, s2) -> Double.compare(s1.getPercentage(), s2.getPercentage()))
                .orElse(null);
    }

}
