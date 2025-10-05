package com.example.studentportal.config;

import com.example.studentportal.model.Student;
import com.example.studentportal.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initStudents(StudentRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Student s1 = new Student();
                s1.setName("Alice Johnson");
                s1.setContact("alice"); // matches student username
                s1.setCity("Springfield");
                s1.setMath(85);
                s1.setScience(90);
                s1.setEnglish(88);
                repo.save(s1);

                Student s2 = new Student();
                s2.setName("Bob Smith");
                s2.setContact("bob"); // matches student username
                s2.setCity("Rivertown");
                s2.setMath(72);
                s2.setScience(78);
                s2.setEnglish(80);
                repo.save(s2);
            }
        };
    }
}
