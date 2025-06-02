package by.duzh.jse.io.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Formatter;
import java.util.UUID;

public class CloseStreamTests {
    private static final String TMP_DIR = System.getProperty("java.io.tmpdir");

    private String name;

    @BeforeEach
    public void init() {
        name = new Formatter().format("%s%s.txt", TMP_DIR, UUID.randomUUID()).toString();
    }

    @Test
    public void testClassic() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(name);
            fos.write("Test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testAutomaticResourceManagement() {
        try (FileOutputStream fos = new FileOutputStream(name)) {
            fos.write("Test".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
