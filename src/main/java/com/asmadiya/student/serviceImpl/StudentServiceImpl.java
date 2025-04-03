package com.asmadiya.student.service;

import com.asmadiya.student.entity.Student;
import com.asmadiya.student.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student getTopPercentStudentInState(String state) {
        List<Student> studentsInState = studentRepository.findByState(state);
        return studentsInState.stream()
                .max((s1, s2) -> Double.compare(s1.getPercentage(), s2.getPercentage()))
                .orElse(null);
    }

    @Override
    public Student getTopperInAllStates() {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .max((s1, s2) -> Double.compare(s1.getPercentage(), s2.getPercentage()))
                .orElse(null);
    }
}
