package by.duzh.springframework.jdbc.core;

import by.duzh.springframework.jdbc.AbstractDatabaseTest;
import by.duzh.springframework.jdbc.core.beans.Foo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

public class JdbcTemplateTest extends AbstractDatabaseTest {

    private static final Logger logger = Logger.getLogger(JdbcTemplateTest.class.getName());

    private JdbcTemplate jdbcTemplate; // thread-safe

    @BeforeEach
    public void setUp() {
        super.setUp();
        jdbcTemplate = new JdbcTemplate(db);
    }

    private final RowMapper<Foo> fooRowMapper = (resultSet, rowNum) -> new Foo(resultSet.getLong("id"),
            resultSet.getString("name"), resultSet.getInt("size"));

    @Test
    void testQuery() {
        // use query
        List<Foo> fooList = jdbcTemplate.query("select id, name, size from t_foo order by id", fooRowMapper);
        assertTrue(fooList.get(0).id == 0 && "Belarus".equals(fooList.get(0).name));
        assertTrue(fooList.get(1).id == 1 && "Poland".equals(fooList.get(1).name));

    }

    @Test
    void testQueryForList() {
        //jdbcTemplate.queryForList("")

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select id, name, size from t_foo order by id");
        assertTrue(mapList.get(0).get("ID").equals(0) && "Belarus".equals(mapList.get(0).get("NAME")));
        assertTrue(mapList.get(1).get("ID").equals(1) && "Poland".equals(mapList.get(1).get("NAME")));
    }

    @Test
    void testQueryForObject() {
        // one field
        String name = jdbcTemplate.queryForObject("select name from t_foo where id = ?", String.class, 1);
        assertEquals("Poland", name);

        // get a single object by id
        Foo foo = jdbcTemplate.queryForObject("select id, name, size from t_foo where id = ?", fooRowMapper, 1);
        assertTrue(foo.id == 1 && "Poland".equals(foo.name));

        // rows count
        int count = jdbcTemplate.queryForObject("select count(*) from t_foo", Integer.class);
        assertEquals(2, count);
    }

    @Test
    void testQueryForMap() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    void testQueryRowSet() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }

    @Test
    void execute() { // run any arbitrary SQL
        // DDL statements
        jdbcTemplate.execute("create table t_buzz (id integer, name varchar(100))");

        // Invoke a stored procedure
        //jdbcTemplate.update("call SUPPORT.REFRESH_ACTORS_SUMMARY(?)", 123);
    }

    @Test
    void update() {
        // INSERT
        final String INSERT_SQL = "insert into t_foo (name, size) values (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreator psc = connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
            ps.setString(1, "Germany    ");
            ps.setInt(2, 100);
            return ps;
        };
        jdbcTemplate.update(INSERT_SQL, psc, keyHolder);
        System.out.println(keyHolder.getKey());

        // UPDATE
        jdbcTemplate.update("update t_foo set name = ?, size = ? where id = ?", "Germany", 500, 2);

        // DELETE
        var count = jdbcTemplate.update("delete from t_foo where id = ?", 1);
        assertEquals(1, count);
    }

    @Test
    void batchUpdate() {
        final int SIZE = 1_000;

        final String INSERT_SQL = "insert into t_batch (name, size) values (?, ?)";
        var pss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, "book -" + i);
                ps.setInt(2, 100 + i);
            }

            @Override
            public int getBatchSize() {
                return SIZE;
            }
        };

        jdbcTemplate.batchUpdate(INSERT_SQL, pss);

        int count = jdbcTemplate.queryForObject("select count(*) from t_batch", Integer.class);
        assertEquals(SIZE, count);
    }

    @Test
    void lob() {
        // add
        var count = jdbcTemplate.execute(
                "INSERT INTO t_lob (a_clob, a_blob) VALUES (?, ?)",
                new AbstractLobCreatingPreparedStatementCallback(new DefaultLobHandler()) {
                    @Override
                    protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException, DataAccessException {
                        //lobCreator.setClobAsCharacterStream(ps, 1, clobReader, (int)clobIn.length());
                        lobCreator.setClobAsString(ps, 1, "Foo");
                        //lobCreator.setBlobAsBinaryStream(ps, 2, blobIs, (int)blobIn.length());
                        lobCreator.setBlobAsBytes(ps, 2, "Bar".getBytes());
                    }
                }
        );
        assertEquals(1, count.intValue());

        // read
        var res = jdbcTemplate.queryForObject("select a_clob, a_blob from  t_lob where id = ?", new RowMapper<Map<String, Object>>() {
            @Override
            public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                var lobHandler = new DefaultLobHandler();

                var map = new HashMap<String, Object>();
                String cLob = lobHandler.getClobAsString(rs, "a_clob");
                byte[] bLob = lobHandler.getBlobAsBytes(rs, "a_blob");
                map.put("CLOB", cLob);
                map.put("BLOB", bLob);
                return map;
            }
        }, 0);

        assertEquals("Foo", res.get("CLOB"));
        assertArrayEquals("Bar".getBytes(), (byte[]) res.get("BLOB"));
    }

    @Test
    public void test() {
        logger.warning("WARNING!!! Test is not implemented yet!");
    }
}