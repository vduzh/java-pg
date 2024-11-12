package by.duzh.springframework.app.dao.foo.jdbc;

import by.duzh.springframework.app.dao.foo.FooDao;
import by.duzh.springframework.app.model.foo.Foo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.Map;

// 1. Annotate the class with @Repository.
// 2. Annotate the DataSource setter method with @Autowired.
// 3. Create a new JdbcTemplate with the DataSource.

//https://www.logicbig.com/tutorials/spring-framework/spring-data-access-with-jdbc/sql-update.html

@Repository
@Profile("jdbc")
public class FooDaoImpl implements FooDao {
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Foo findById(Long id) {
        System.out.println(getClass() + " findById is working..." );
        Map<String, Long> paramMap = Collections.singletonMap("id", id);

        return jdbcTemplate.queryForObject("select * from t_foo where id = :id", paramMap,
                (rs, rowNum) -> new Foo(rs.getLong("id"), rs.getString("name"), rs.getInt("size")));
    }
}