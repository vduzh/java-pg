package by.duzh.springframework.jdbc.core.namedparam;

import by.duzh.springframework.jdbc.AbstractDatabaseTest;
import by.duzh.springframework.jdbc.core.beans.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NamedParameterJdbcTemplateTest extends AbstractDatabaseTest {

    private NamedParameterJdbcTemplate jdbcTemplate; // thread-safe

    @BeforeEach
    public void setUp() {
        super.setUp();
        jdbcTemplate = new NamedParameterJdbcTemplate(db);
    }

    private final RowMapper<Foo> fooRowMapper = (resultSet, rowNum) -> new Foo(resultSet.getLong("id"),
            resultSet.getString("name"), resultSet.getInt("size"));

    @Test
    void returnSingleValue() {
        // one field using Map of params
        Map<String, ?> paramMap = Collections.singletonMap("id", 1);
        String name = jdbcTemplate.queryForObject("select name from t_foo where id = :id", paramMap, String.class);
        assertEquals("Poland", name);

        // get a single object by id using ParamSource
        SqlParameterSource paramSource = new MapSqlParameterSource("id", 1);
        Foo foo = jdbcTemplate.queryForObject("select id, name, size from t_foo where id = :id", paramSource, fooRowMapper);
        assertTrue(foo.id == 1 && "Poland".equals(foo.name));

        // rows count
        int count = jdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from t_foo", Integer.class);
        assertEquals(2, count);

        paramMap = Collections.singletonMap("size", 0);
        count = jdbcTemplate.queryForObject("select count(*) from t_foo where size > :size", paramMap, Integer.class);
        assertEquals(2, count);

    }

    @Test
    void list() {
        // use query
        List<Foo> fooList = jdbcTemplate.query("select id, name, size from t_foo order by id", fooRowMapper);
        assertEquals(2, fooList.size());
        assertTrue(fooList.get(0).id == 0 && "Belarus".equals(fooList.get(0).name));
        assertTrue(fooList.get(1).id == 1 && "Poland".equals(fooList.get(1).name));

        // use queryForList
        Map<String, Integer> paramMap = paramMap = Collections.singletonMap("id", 0);
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList(
                "select id, name, size from t_foo where id > :id  order by id", paramMap);
        assertTrue(mapList.get(0).get("ID").equals(1) && "Poland".equals(mapList.get(0).get("NAME")));
    }

    @Test
    void insert() {
        final String INSERT_SQL = "insert into t_foo (name, size) values (:name, :size)";
        SqlParameterSource paramSource = new MapSqlParameterSource("name", "Germany").addValue("size", 500);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_SQL, paramSource, keyHolder);
        assertEquals(2, keyHolder.getKey());
    }

    @Test
    void update() {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("size", 30)
                .addValue("id", 1);
        int count = jdbcTemplate.update("update t_foo set size = :size where id = :id", paramSource);
        assertEquals(1, count);
    }

    @Test
    void delete() {
        Map<String, Long> paramMap = Collections.singletonMap("id", 1L);
        int count = jdbcTemplate.update("delete from t_foo where id = :id", paramMap);
        assertEquals(1, count);
    }

    @Test
    void execute() { // run any arbitrary SQL
        // DDL statements
        jdbcTemplate.getJdbcTemplate().execute("create table t_buzz (id integer, name varchar(100))");

        //Invoke a stored procedure
        //jdbcTemplate.getJdbcTemplate().update("call SUPPORT.REFRESH_ACTORS_SUMMARY(?)", 123);
    }

    @Test
    void batchUpdate() {
        final int SIZE = 1_000;
        final String INSERT_SQL = "insert into t_batch (name, size) values (:name, :size)";

        var parameterSources = new MapSqlParameterSource[SIZE];
        for (var i = 0; i < SIZE; i++) {
            parameterSources[i] = new MapSqlParameterSource("name", "book-" + i).addValue("size", i);
        }

        jdbcTemplate.batchUpdate(INSERT_SQL, parameterSources);

        int count = jdbcTemplate.getJdbcTemplate().queryForObject("select count(*) from t_batch", Integer.class);
        assertEquals(SIZE, count);
    }
}
