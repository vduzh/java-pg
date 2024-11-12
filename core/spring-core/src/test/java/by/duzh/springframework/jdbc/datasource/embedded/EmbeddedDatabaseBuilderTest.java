package by.duzh.springframework.jdbc.datasource.embedded;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmbeddedDatabaseBuilderTest {
    private EmbeddedDatabase db;

    @AfterAll
    public void tearDown() {
        if (db != null) {
            db.shutdown();
        }
    }

    @Test
    void createHSQLMinimal() throws Exception {
        db = new EmbeddedDatabaseBuilder(new ClassRelativeResourceLoader(this.getClass()))
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .addDefaultScripts()
                .build();

        try (var connection = db.getConnection()) {
        }
    }

    @Test
    void createHSQLWithRelativeScripts() throws Exception {
        db = new EmbeddedDatabaseBuilder(new ClassRelativeResourceLoader(this.getClass()))
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .addScript("hsql/schema.sql")
                .addScripts("foo.sql", "bar.sql")
                .build();
        try (var connection = db.getConnection()) {
        }
    }

    @Test
    void createHSQLWithClassPathScripts() throws Exception {
        String PATH = "classpath:by/duzh/springframework/jdbc/datasource/embedded/";

        db = new EmbeddedDatabaseBuilder(new ClassRelativeResourceLoader(this.getClass()))
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .addScript(PATH + "hsql/schema.sql")
                .addScripts(PATH + "foo.sql", PATH + "bar.sql")
                .build();
        try (var connection = db.getConnection()) {
        }
    }
}
