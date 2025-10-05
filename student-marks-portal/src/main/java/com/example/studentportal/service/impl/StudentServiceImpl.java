package com.example.studentportal.service.impl;

import com.example.studentportal.model.Student;
import com.example.studentportal.repository.StudentRepository;
import com.example.studentportal.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    public StudentServiceImpl(StudentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Student save(Student s) {
        return repository.save(s);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Student> findByContact(String contact) {
        return repository.findByContact(contact);
    }
}
