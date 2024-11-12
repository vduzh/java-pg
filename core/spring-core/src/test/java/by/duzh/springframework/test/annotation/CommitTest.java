package by.duzh.springframework.test.annotation;

import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;

public class CommitTest {
    @Commit
    @Test
    void name() {
        throw new RuntimeException();
    }
}
