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

---

## 6. Entity Relationships - The Real Deal!

Database lo tables okadanikokati connect ayi untayi kada? For example, `students` table, `addresses` table ki link ayi undochu. Ee connection ne manam **Relationship** antam. Hibernate lo idi chala powerful feature.

Relationships lo 4 main types untayi:
1.  **One-to-One:** Oka student ki okate address.
2.  **One-to-Many:** Oka teacher chala courses cheptaru.
3.  **Many-to-One:** Chala courses ni oke teacher cheptaru (idi just One-to-Many ki reverse).
4.  **Many-to-Many:** Chala students chala courses lo join avvochu.

Ippudu manam first three gurinchi detail ga chuddam.

### i. `@OneToOne` Relationship

**Scenario:** Prathi `Student` ki, వాళ్లకు సంబంధించిన `Address` okkate untundi.

**Ela chestam?**
1.  Manam `Student` entity tho పాటు, `Address` ane kottha entity ni create cheyali.
2.  `Student` class lo, `Address` type tho oka variable pettali.
3.  Aah variable meeda `@OneToOne` annotation pettali.

**Foreign Key:**
Database lo, ee rendu tables ni link cheyadaniki, `student` table lo `address_id` ane oka extra column pedatham. Deenne **foreign key** antam. Ee column `address` table lo unna primary key ni point chestundi.

Ee foreign key column ni specify cheyadaniki, manam `@JoinColumn` ane annotation vaadatham.

#### Example Code Snippet:

**`Student.java` (Owning side - ikkade foreign key untundi)**
```java
@Entity
public class Student {
    // ... other fields like id, firstName, etc.

    @OneToOne(cascade = CascadeType.ALL) // Address ni save chesthe Student kuda save avvali
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    // ... getters and setters
}
```
- **`cascade = CascadeType.ALL`**: Ante, "nenu student ni save chesthe, daanitho paatu address ni kuda save chesey" ani cheppadam.
- **`@JoinColumn(name = "address_id")`**: `student` table lo create avvalsina foreign key column peru.

**`Address.java`**
```java
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;

    // ... getters and setters
}
```
Ippudu meeru oka `Student` object ni save chesthe, Hibernate first `Address` object ni save chesi, daani ID theeskuni, aah ID ni `student` table lo unna `address_id` column lo petti, tarvatha `Student` ni save chestundi. Antha automatic!

### ii. `@OneToMany` & `@ManyToOne` Relationships

Ee rendu ఎప్పుడూ a kalise a untayi. Okati lekunda inkoti undadu.

**Scenario:** Oka `Teacher` chala `Course`s teeskuntaru.
- `Teacher` side nunchi chusthe, idi **One-to-Many** (oka teacher, chala courses).
- `Course` side nunchi chusthe, idi **Many-to-One** (chala courses, oke teacher).

**Rule of Thumb:** Foreign key ఎప్పుడూ "Many" side unna table lo ne untundi. Ante, `course` table lo `teacher_id` ane column untundi. Endukante, prathi course ki teacher evaro cheppagalam. Kaani teacher table lo course_id pettalem, endukante aayana chala courses cheptaru.

So, `@ManyToOne` unna side ni **Owning Side** antam.

#### Example Code Snippet:

**`Course.java` (Owning Side - "Many" side)**
```java
@Entity
public class Course {
    // ... id, courseName, etc.

    @ManyToOne(fetch = FetchType.LAZY) // Default LAZY, performance ki manchidi
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    // ... getters and setters
}
```
- Ikkada `course` table lo `teacher_id` ane foreign key create avutundi.

**`Teacher.java` ("One" side)**
```java
@Entity
public class Teacher {
    // ... id, teacherName, etc.

    @OneToMany(mappedBy = "teacher")
    private Set<Course> courses = new HashSet<>();

    // ... getters and setters
}
```
- **`mappedBy = "teacher"`**: Idi chala important. Idi Hibernate ki cheptundi: "Hey, ee relationship ki sambandinchina foreign key `Course` class lo unna `teacher` ane variable daggara undi. Ikkada nuvvu kottha column create cheyaku."
- `mappedBy` pettakapothe, Hibernate confuse ayyi, inko extra link table create chesestundi. So, don't forget it!

Ee relationships ni manam project lo implement chesi chusthe, meeku inka clear ga artham avutundi.