package com.example.hibernatedemo;

import com.example.hibernatedemo.entity.Address;
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
            // Create an Address
            Address address = new Address("123 Main St", "Hyderabad");

            // Create a Student and link the Address
            Student student = new Student("Ravi", "Teja", "ravi.teja@example.com");
            student.setAddress(address);

            // Save the student. Because of CascadeType.ALL, the address will also be saved.
            repository.save(student);

            // Fetch all students and display their address
            System.out.println("Students found with findAll():");
            System.out.println("-------------------------------");
            for (Student s : repository.findAll()) {
                System.out.println(s.getFirstName() + " " + s.getLastName() + " lives at: " + s.getAddress().getCity());
            }
            System.out.println("");
        };
    }
}
