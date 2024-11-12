package by.duzh.springframework.jdbc.object;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;
import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringJUnitConfig(value = {DataSourceTestConfig.class, SqlUpdateTest.UpdateFooName.class})
public class SqlUpdateTest {
    private static final String SQL = "update t_foo set name = ?, size = ? where id = ?";

    public static class UpdateFooName extends SqlUpdate {
        public UpdateFooName(DataSource dataSource) {
            setDataSource(dataSource);
            setSql(SQL);
            declareParameter(new SqlParameter("name", Types.VARCHAR));
            declareParameter(new SqlParameter("size", Types.INTEGER));
            declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        public int executeUpdate(Long id, String name, int size) {
            return update(name, size, id);
        }
    }

    @Autowired
    private UpdateFooName updateFooName;

    @Autowired
    private DataSource dataSource;

    @Test
    void subClassing() {
        int count = updateFooName.executeUpdate(1L, "TEST", 15);
        assertEquals(1, count);
    }

    @Test
    void useClass() {
        SqlUpdate sqlUpdate = new SqlUpdate();
        sqlUpdate.setDataSource(dataSource);

        sqlUpdate.setSql(SQL);
        sqlUpdate.declareParameter(new SqlParameter("name", Types.VARCHAR));
        sqlUpdate.declareParameter(new SqlParameter("size", Types.INTEGER));
        sqlUpdate.declareParameter(new SqlParameter("id", Types.INTEGER));

        sqlUpdate.compile();

        int count = sqlUpdate.update("TEST2", 30, 1);
        assertEquals(1, count);
    }
}
