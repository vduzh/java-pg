package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.School;
import by.duzh.springframework.springdata.jpa.repository.PositionRepository;
import by.duzh.springframework.springdata.jpa.repository.SchoolRepository;
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
public class SchoolRepositoryTest {
    @Autowired
    private SchoolRepository repository;

    @Test
    void findAll() {
        var schools = repository.findAll();
        assertThat(schools).hasSize(2);
    }

    @Test
    void getById() {
        var school = repository.findById(1);
        assertTrue(school.isPresent());
    }

    @Rollback
    @Test
    void add() {
        var school = School.builder().name("Test").build();
        var res = repository.save(school);
        assertNotNull(res.getId());
    }
}
