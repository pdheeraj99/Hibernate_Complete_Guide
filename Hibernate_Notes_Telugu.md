# Hibernate Notes (Telugu lo)

Namaste, raja! Ee notes lo manam Hibernate gurinchi antha a to z thelusukundam. Nenu meeku antha clear ga, step-by-step explain chestanu. Doubt em undadu, chala easy ga artham aipotundi. Let's start!

## 1. Hibernate Ante Enti? Enduku Vaadali? (Introduction)

First question - "Asalu ee Hibernate endi, bhayya?"

Simple ga cheppalante, **Hibernate anedi Java ki aey, database ki madhyalo oka middleman (vaaduka bhasha lo cheppalante "broker") laantidi.** Deenine manam **ORM (Object-Relational Mapping)** tool antam.

- **Object-Relational Mapping (ORM) ante enti?**
  - Manam Java lo code raasthe, anni **objects** (e.g., `Student`, `Employee`) create chestam.
  - Database lo data antha **tables** (relations) lo untundi.
  - Ee Java objects ni, aah database tables ki **map** (connect) chese process ne ORM antaru.

- **Enduku Vaadali? Deeni Valla Use Enti?**
  - Imagine meeru direct ga database tho matladalante, chala **JDBC (Java Database Connectivity)** code rayali. Antha chala pedda pani and chala boring code.
  - **Hibernate ee pani antha easy chestundi.** Meeru SQL queries aey rayakarledu. Just Java objects tho aey aadukondi, Hibernate aey migathadi antha chuskuntundi. Simple ga, Hibernate manaki ee boring JDBC code nunchi aey vimukthi kalpisthundi.

## 2. Prerequisites (Manaki Em Kavali?)

Hibernate start chese mundu, meeku konni basics thelise undali:
- **Java Fundamentals:** Classes, Objects, Collections.
- **Maven/Gradle:** Basic idea of how to add dependencies.
- **Database:** SQL basics (tables, columns, primary keys).

## 3. Core Concepts (Mukhyamaina Vishayalu)

- **`SessionFactory`**: Idi Hibernate ki factory laantidi. Okkasari create chestam, app antha vaadukuntam. Chala heavy object.
- **`Session`**: Idi database ki oka connection laantidi. Factory nunchi create aavutundi. Lightweight and short-lived. Prathi operation ki oka session create chesi, pani aipogane close chestaam.
- **`Transaction`**: "Do it all or do nothing." Anni operations kalipi okesaari cheyadaniki. Success aithe `commit`, fail aithe `rollback`.
- **`Entity`**: Oka simple Java class (POJO), kaani database lo oka table ni represent chestundi.

---

## 4. Entity Mapping - Asalu Magic Ikkade Undi!

Java class ni table ga ela maarchali? Adi Hibernate ki ela cheppali? Daanike manam **Annotations** vaadatham. Ivi mana class ki extra information isthayi.

### Key Annotations:

- **`@Entity`**:
  - Class meeda pedatham. Idi Hibernate ki cheptundi: "Hey, ee class oka table ra bhai!"
  - Example: `public class Student` meeda `@Entity` pedithey, Hibernate `student` ane table kosam chustundi.

- **`@Table(name = "students_info")`**:
  - Optional. By default, class name ne table name ga theeskuntundi. Meeku vere peru kavali anukunte, `@Table` vaadi cheppochu.

- **`@Id`**:
  - Prathi table ki oka **primary key** untundi kada? Alaage prathi entity class lo oka variable `@Id` ga undali. Idi unique ga identify cheyadaniki.
  - Example: `private Long id;` meeda `@Id` pedatham.

- **`@GeneratedValue(strategy = GenerationType.IDENTITY)`**:
  - `@Id` unna field ki idhi pedatham. Ante, "ee ID ni nuvve generate chesko" ani manam database ki cheptunnam. Database aey `1, 2, 3...` ani automatically create cheskuntundi.

- **`@Column(name = "student_name")`**:
  - Optional. By default, variable name ne column name ga theeskuntundi (`firstName` ante `first_name`). Meeku vere peru kavali anukunte, `@Column` vaadachu.

### Example Code Snippet:

```java
import javax.persistence.*; // Ee package lo anni annotations untayi

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email; // Ikkada @Column pettaledu, so column name 'email' gaane untundi
}
```

## 5. Spring Data JPA - Inka Simple!

Manam Spring Boot vaaduthunnam kabatti, manaki inko super power undi: **Spring Data JPA**.

- **Entidi?** Idi Hibernate meeda oka wrapper laantidi. Pani ni inka, inka, inka easy chestundi.
- Manam `Session` and `Transaction` gurinchi aey pattinchukonavasaram ledu. Spring aey antha chuskuntundi.

### JpaRepository

- Manam cheyalsindalla, oka **interface** create chesi, daanni `JpaRepository` tho extend cheyali.
- Anthe! **Magic!** `save()`, `findById()`, `findAll()`, `deleteById()` lanti anni basic methods manaki free ga vachestayi. Manam code rayakarledu.

### Example Code Snippet:

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Anthe, ikkada em rayakarledu!
    // Manaki kaavalsina methods anni vachesinatte.
    // Student -> entity type
    // Long -> primary key type
}
```

Eppudu manam `StudentRepository` ni mana code lo vaadukuni, direct ga `repository.save(studentObject)` ani anochu. Database lo aey save aipotundi. SQL query rayaledu, session open cheyaledu, transaction manage cheyaledu. Antha Spring chuskundi.

This is the real power of using Hibernate with Spring Boot. Manam business logic meeda focus cheyochu, ee boring database code meeda kaadu.