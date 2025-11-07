# Hibernate Project Guide (Developer Diary)

Ok raja, ippudu manam developer hats pettukundam. Theory antha `Hibernate_Notes_Telugu.md` lo chusam ga, ippudu asalu pani cheddam. Nenu oka project ni ground-up nunchi ela build chestano, step-by-step meeku chupista. Naa thought process ni follow avvandi, you'll get the hang of it.

## Step 0: Project Setup - Aalochanalu (My Thoughts)

"Okay, first em cheyali? Clean ga oka Spring Boot project setup cheskovali. Daaniki `spring-boot-starter-data-jpa` kavali, endukante adhi Hibernate ni theeskostadi. Database em vadudam? Simple ga start cheyataniki `H2` in-memory database aipatadi. Real project lo MySQL or PostgreSQL vadutharu, but for now, H2 is the king. So, ee dependencies anni `pom.xml` lo add cheyali."

"Next enti? Database connection setup cheyali. Adi `application.properties` file lo untadi. H2 database ki URL, username, password lanti details ivvali. Also, Hibernate ki cheppali, 'nuvvu tables ni automatic ga create chesey' ani. Daaniki `ddl-auto=update` ane property set chesta."

"Ivi aipoyaka, asalu code start cheddam. First, oka model/entity class create cheskovali. `Student` anukundam. Daaniki `@Entity` ani petti, adi oka table ani Hibernate ki chepdam. Tarvatha, daaniకోసం oka `JpaRepository` create chesthe, CRUD operations anni free ga vachestayi. Finally, antha work avutundo ledo test cheyataniki, oka `CommandLineRunner` tho app start ayinapudu oka student ni save chesi chusta."

Ee initial thoughts tho, let's start building the project.

---

## Step 1: `pom.xml` - The Foundation

"Okay, project ki em kavalo anni ikkada define cheyali. Spring Boot parent, JPA starter, H2 database. Anni add chesesa."

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.5</version>
        <relativePath/>
    </parent>
    <groupId>com.example</groupId>
    <artifactId>hibernate-demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>hibernate-demo</name>
    <description>A simple Hibernate project</description>
    <properties>
        <java.version>11</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## Step 2: `application.properties` - Configuring the Database

"Ippudu database connection details ivvali. `src/main/resources` lo ee file create chesi, H2 database settings pedadam."

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Ee line chala important. Idi Hibernate ki cheptundi,
# entity classes chusi, table lekapothe create chey, unte update chey ani.
spring.jpa.hibernate.ddl-auto=update

# H2 console ni enable chesthe, manam browser lo database chudochu.
spring.h2.console.enabled=true
```

---

## Step 3: The `Student` Entity - The Heart of Our App

"Time for our Java class. I'll create `Student.java` inside an `entity` package. `@Entity`, `@Id`, `@GeneratedValue` - notes lo cheppina anni annotations ikkada vaadutunna."

File Path: `src/main/java/com/example/hibernatedemo/entity/Student.java`
```java
package com.example.hibernatedemo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    // Constructors, Getters, and Setters...
}
```

---

## Step 4: The `StudentRepository` - The Magic Wand

"Ippudu data save cheyadaniki, theeskovadaniki oka way kavali. Adi ee repository. `repository` package lo create chestunna. Just `JpaRepository` ni extend cheste chalu. Anni methods vachesinatte."

File Path: `src/main/java/com/example/hibernatedemo/repository/StudentRepository.java`
```java
package com.example.hibernatedemo.repository;

import com.example.hibernatedemo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
```

---

## Step 5: `HibernateDemoApplication.java` - Testing Everything

"Okay, antha setup chesam. Ippudu antha kalipi work avutundo ledo chudali. Main application class lo, `CommandLineRunner` ane oka bean create chesta. Application start avvagane, idi run avutundi. Andulo, manam create chesina `StudentRepository` ni use chesi, oka student ni save chesi, tarvatha andari students ni print chesi chuddam."

File Path: `src/main/java/com/example/hibernatedemo/HibernateDemoApplication.java`
```java
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
            // Oka kottha student ni save cheddam
            repository.save(new Student("Raja", "Kumar", "raja.kumar@example.com"));

            // Database lo unna students andariని fetch cheddam
            System.out.println("Students found with findAll():");
            System.out.println("-------------------------------");
            for (Student student : repository.findAll()) {
                System.out.println(student.getFirstName() + " " + student.getLastName());
            }
            System.out.println("");
        };
    }
}
```

"Done! Ippudu `mvn spring-boot:run` command isthe, application start avvali, and console lo 'Raja Kumar' ani print avvali. Appudu mana setup 100% success ayinattu."