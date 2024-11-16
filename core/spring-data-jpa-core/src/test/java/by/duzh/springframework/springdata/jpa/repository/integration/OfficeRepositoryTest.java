package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.entity.Office;
import by.duzh.springframework.springdata.jpa.repository.OfficeRepository;
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
public class OfficeRepositoryTest {
    @Autowired
    private OfficeRepository repository;

    @Test
    void findAll() {
        var repositories = repository.findAll();
        System.out.println(repositories);
        assertThat(repositories).hasSize(2);
    }

    @Test
    void getById() {
        var office = repository.findById(1);
        assertTrue(office.isPresent());
    }

    @Test
    void addCompany() {
        var office = Office.builder()
                .name("Test")
                .build();

        office = repository.saveAndFlush(office);
        System.out.println(office);
        assertNotNull(office.getId());
    }
}
