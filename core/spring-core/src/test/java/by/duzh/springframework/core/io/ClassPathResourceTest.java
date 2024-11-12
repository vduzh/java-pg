package by.duzh.springframework.core.io;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClassPathResourceTest {

    @Test
    void name() throws Exception {
        // the absolute path within the class path
        String location = String.format("%s/test.txt", getClass().getPackageName().replace('.', '/'));

        var resource = new ClassPathResource(location);
        assertTrue(resource.exists());
    }
}
