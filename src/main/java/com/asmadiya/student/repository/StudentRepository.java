package com.asmadiya.student.repository;

import com.asmadiya.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Custom method to find students by state
    List<Student> findByState(String state);
}
