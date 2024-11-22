package by.duzh.springframework.springdata.jpa.repository;

import by.duzh.springframework.springdata.jpa.ApplicationRunner;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Transactional
@Rollback
@SpringBootTest(classes = ApplicationRunner.class)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void findAll() {
        var repositories = repository.findAllBy();
        assertThat(repositories).hasSize(8);
    }

    @Test
    void findFirstByOrderByNameAsc() {
        var role = repository.findFirstByOrderByNameAsc();
        assertTrue(role.isPresent());
        role.ifPresent(r -> assertThat(r.getName()).isEqualTo("Administrator"));
    }

    @Test
    void findTop2ByOrderByNameAsc() {
        var roles = repository.findTop2ByOrderByNameAsc();
        assertThat(roles).hasSize(2);
    }

    @Test
    void findAllIgnoreCase() {
        var repositories = repository.findAllIgnoreCase("pm");
        assertThat(repositories).hasSize(1);
    }

    @Test
    void findById() {
        var role = repository.findById(1);
        assertTrue(role.isPresent());
    }

    @Test
    void findByIdentifier() {
        var role = repository.findByIdentifier(2);
        assertTrue(role.isPresent());
    }

    @Test
    void sort() {
        Sort sort = Sort.by("name").descending().and(Sort.by("id").descending());

        var roles = repository.findTop2By(sort);
        assertThat(roles).hasSize(2);
    }

    @Test
    void pageable() {
        var pageable = PageRequest.of(1, 1, Sort.by("name").descending());
        var roles = repository.findAllBy(pageable);
        assertThat(roles).hasSize(1);
    }

    @Test
    void sliceNotUsedVeryOften() {
        var pageable = PageRequest.of(1, 2, Sort.by("name").descending());
        var slice = repository.findAllByNameNotContaining("'", pageable);
        slice.forEach(r -> System.out.println(r.getName()));

        while (slice.hasNext()) {
            slice = repository.findAllByNameNotContaining("'", slice.nextPageable());
            slice.forEach(r -> System.out.println(r.getName()));
        }
    }

    @Test
    void pageUsedOften() {
        var pageable = PageRequest.of(1, 2, Sort.by("name").descending());
        var roles = repository.findAllByIdAfter(0, pageable);
    }
}
