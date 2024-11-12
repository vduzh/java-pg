package by.duzh.springframework.jdbc.datasource.embedded;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

public class EmbeddedDatabaseTest {
    private EmbeddedDatabase db;

    @BeforeEach
    public void setUp() {
        db = new EmbeddedDatabaseBuilder(new ClassRelativeResourceLoader(this.getClass()))
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .addDefaultScripts()
                .build();
    }

    @AfterEach
    public void tearDown() {
        if (db != null) {
            db.shutdown();
        }
    }

    @Test
    void test() throws Exception {
        var name = new JdbcTemplate(db)
                .queryForObject("select name from TEST.t_foo where id = ?", String.class, 1);

        Assertions.assertTrue("Ford".equals(name));
    }
}
