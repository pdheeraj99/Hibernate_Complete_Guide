package com.example.hibernatedemo;

import com.example.hibernatedemo.entity.Student;
import com.example.hibernatedemo.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HibernateDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(StudentRepository repository) {
        return (args) -> {
            // Save a new student
            repository.save(new Student("Raja", "Kumar", "raja.kumar@example.com"));

            // Fetch all students
            System.out.println("Students found with findAll():");
            System.out.println("-------------------------------");
            for (Student student : repository.findAll()) {
                System.out.println(student.getFirstName() + " " + student.getLastName());
            }
            System.out.println("");
        };
    }
}
