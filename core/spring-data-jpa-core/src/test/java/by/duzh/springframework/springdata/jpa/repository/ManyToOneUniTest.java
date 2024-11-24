package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class ManyToOneUniTest {
    public static final int STUDENT_ID = 14;

    @Autowired
    private StudentRepository repository;

    @Autowired
    private SchoolRepository schoolRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findAll() {
        var students = repository.findAll();
        assertThat(students).hasSize(20);
    }

    @Test
    void findAllHQL() {
        var students = repository.findAllHQL();
        assertThat(students).hasSize(20);

        var schoolName = "Green School";
        students = repository.findAllBySchoolHQL(schoolName);
        assertThat(students).hasSize(2);

        students = repository.findAllBySchoolWithLeftJoinHQL(schoolName);
        assertThat(students).hasSize(2);
    }

    @Test
    void getById() {
        var student = repository.findById(14);
        assertTrue(student.isPresent());
    }

    @Test
    void add() {
        var school = schoolRepository.findById(6);
        assertTrue(school.isPresent());

        var student = Student.builder()
                .name("Test")
                .school(school.get())
                .build();

        var res = repository.save(student);
        assertNotNull(res.getId());
    }

    @Test
    void updateHQL() {
        var countRows = studentRepository.updateStudentNameHQL(STUDENT_ID, "Test");
        assertEquals(1, countRows);

        var student = repository.findById(STUDENT_ID);
        assertTrue(student.isPresent());
        System.out.println(student.get());
    }
}
