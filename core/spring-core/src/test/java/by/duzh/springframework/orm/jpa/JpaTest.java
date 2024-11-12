package by.duzh.springframework.orm.jpa;

import by.duzh.springframework.orm.jpa.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(JpaTransactionManagerTestConfig.class)
public class JpaTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional(readOnly = true)
    void test() {
        assertEquals("Poland", entityManager.find(Foo.class, 1L).getName());
    }
}
