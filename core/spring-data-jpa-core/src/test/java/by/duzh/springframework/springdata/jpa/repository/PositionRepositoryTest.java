package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class PositionRepositoryTest {
    @Autowired
    private PositionRepository repository;

    @Test
    void findAll() {
        var repositories = repository.findAll();
        assertThat(repositories).hasSize(3);
    }

    @Test
    void getById() {
        var role = repository.findById(1);
        assertTrue(role.isPresent());
    }
}
