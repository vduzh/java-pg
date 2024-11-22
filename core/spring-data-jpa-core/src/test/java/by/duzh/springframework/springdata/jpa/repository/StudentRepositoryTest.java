package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Student;
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
public class StudentRepositoryTest {
    @Autowired
    private StudentRepository repository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Test
    void findAll() {
        var students = repository.findAll();
        assertThat(students).hasSize(3);
    }

    @Test
    void getById() {
        var school = repository.findById(1);
        assertTrue(school.isPresent());
    }

    @Rollback
    @Test
    void add() {
        var school = schoolRepository.findById(2);
        assertTrue(school.isPresent());

        var student = Student.builder()
                .name("John Doe")
                .school(school.get())
                .build();

        var res = repository.save(student);
        assertNotNull(res.getId());
    }
}
