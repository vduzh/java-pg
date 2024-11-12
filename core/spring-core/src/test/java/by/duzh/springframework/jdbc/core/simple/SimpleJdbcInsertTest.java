package by.duzh.springframework.jdbc.core.simple;

import by.duzh.springframework.jdbc.AbstractDatabaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleJdbcInsertTest extends AbstractDatabaseTest {

    @Test
    void insert() {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(db)
                .withTableName("t_foo");

        Map<String, Object> params = new HashMap<>();
        params.put("name", "test");
        params.put("size", 100);

        int count = jdbcInsert.execute(params);
        assertEquals(1, count);
    }

    @Test
    void insertAndReturnKey() {
        var jdbcInsert = new SimpleJdbcInsert(db)
                .withTableName("t_foo")
                .usingGeneratedKeyColumns("id");

        var params = new MapSqlParameterSource("name", "test")
                .addValue("size", 100);
        Number key = jdbcInsert.executeAndReturnKey(params);

        assertEquals(2, key.longValue());
    }
}