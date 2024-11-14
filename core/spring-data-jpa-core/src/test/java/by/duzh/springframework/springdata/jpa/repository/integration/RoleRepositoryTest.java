package by.duzh.springframework.springdata.jpa.repository.integration;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import by.duzh.springframework.springdata.jpa.repository.RoleRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void getById() {
        System.out.println("Trying to get the role by its id...");
        var role = repository.findById(1);
        System.out.println("Found: " + role.isPresent());
    }

    @Test
    void save() {
        System.out.println("save");
    }
}
