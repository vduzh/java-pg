package by.duzh.springframework.jdbc.object;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import by.duzh.springframework.jdbc.object.beans.Foo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(DataSourceTestConfig.class)
public class MappingSqlQueryTest {

    public static class FooMappingSqlQuery extends MappingSqlQuery<Foo> {
        protected final static String SQL = "select id, name, size from t_foo where id = ?";

        public FooMappingSqlQuery(DataSource ds) {
            super(ds, SQL);
            declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        protected Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Foo(rs.getLong("id"), rs.getString("name"), rs.getInt("size"));
        }
    }

    public static class FooListMappingSqlQuery extends MappingSqlQuery<Foo> {
        protected final static String SQL = "select id, name, size from t_foo where size > ?";

        public FooListMappingSqlQuery(DataSource ds) {
            super(ds, SQL);
            declareParameter(new SqlParameter("size", Types.INTEGER));
            compile();
        }

        @Override
        protected Foo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Foo(rs.getLong("id"), rs.getString("name"), rs.getInt("size"));
        }
    }

    // NOTE: could be autowired!
    private FooMappingSqlQuery fooQuery;
    private FooListMappingSqlQuery fooListQuery;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.fooQuery = new FooMappingSqlQuery(dataSource);
        this.fooListQuery = new FooListMappingSqlQuery(dataSource);
    }

    @Test
    void findObject() {
        var foo = fooQuery.findObject(1);
        assertNotNull(foo);
        assertEquals(1, foo.id.longValue());
    }

    @Test
    void execute() {
        var list = fooListQuery.execute(0);
        assertEquals(2, list.size());
    }
}
