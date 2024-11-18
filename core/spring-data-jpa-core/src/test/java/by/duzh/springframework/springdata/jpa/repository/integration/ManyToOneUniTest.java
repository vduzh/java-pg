package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Student;
import by.duzh.springframework.springdata.jpa.repository.SchoolRepository;
import by.duzh.springframework.springdata.jpa.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class ManyToOneUniTest {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    void findAll() {
        var students = repository.findAll();
        assertThat(students).hasSize(20);
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
}
