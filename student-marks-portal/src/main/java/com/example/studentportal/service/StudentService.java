package com.example.studentportal.service;

import com.example.studentportal.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Student> findAll();
    Optional<Student> findById(Long id);
    Student save(Student s);
    void deleteById(Long id);
    Optional<Student> findByContact(String contact);
}
