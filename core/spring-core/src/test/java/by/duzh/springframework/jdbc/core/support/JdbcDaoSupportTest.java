package by.duzh.springframework.jdbc.core.support;

import by.duzh.springframework.jdbc.DataSourceTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

// JdbcDaoSupport class is provided as a convenience only
@SpringJUnitConfig(classes = {DataSourceTestConfig.class, JdbcDaoSupportTest.AppConfig.class})
public class JdbcDaoSupportTest {

    @Configuration
    static class AppConfig {
        @Bean
        @Autowired
        public JdbcDaoSupport dao(DataSource ds) {
            JdbcDaoSupport res = new JdbcDaoSupport() {
            };
            res.setDataSource(ds);
            return res;
        }
    }

    @Autowired
    JdbcDaoSupport dao;

    @Test
    void name() {
        int count = dao.getJdbcTemplate().queryForObject("select count(*) from t_foo", Integer.class);
        assertEquals(2, count);
    }
}
