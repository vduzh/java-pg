package by.duzh.springframework.springdata.jpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Rollback
//@Commit
@SpringBootTest
public class SomeTest {
    @Test
    void getById() {
        System.out.println("getById");
    }

    @Test
    void save() {
        System.out.println("save");
    }
}
