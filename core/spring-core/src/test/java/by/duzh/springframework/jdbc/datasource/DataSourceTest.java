package by.duzh.springframework.jdbc.datasource;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.datasource.SmartDataSource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import java.sql.Driver;

public class DataSourceTest {
    private final String HSQLDB_DRIVER_NAME = "org.hsqldb.jdbc.JDBCDriver";

    @Test
    void TEST_ENV_driverManagerDataSource() throws Exception {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(HSQLDB_DRIVER_NAME);
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
    }

    @Test
    void TEST_ENV_simpleDriverDataSource() throws Exception {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver((Driver) Class.forName(HSQLDB_DRIVER_NAME).newInstance());
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb1");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
    }

    @Test
    void TEST_ENV_singleConnectionDataSource() throws Exception {
        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName(HSQLDB_DRIVER_NAME);
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb3");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
        dataSource.destroy();
    }

    // DBCP
    @Test
    void PROD_ENV_commonsDatabaseConnectionPooling() throws Exception {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(HSQLDB_DRIVER_NAME);
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb2");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
        dataSource.close();
    }

    // C3P0
    @Test
    void PROD_ENV_c3p0() throws Exception {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(HSQLDB_DRIVER_NAME);
        dataSource.setJdbcUrl("jdbc:hsqldb:mem:mymemdb2");
        dataSource.setUser("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
        dataSource.close();
    }

    // Hikari
    @Test
    void PROD_ENV_hikari() throws Exception {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(HSQLDB_DRIVER_NAME);
        dataSource.setJdbcUrl("jdbc:hsqldb:mem:mymemdb3");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        try (var connection = dataSource.getConnection()) {
            ;
        }
        dataSource.close();
    }

    @Test
    void PROD_ENV_jndiDataSourceLookup() {
        var dataSource = new JndiDataSourceLookup().getDataSource("jdbc/DataSource");
    }

    @Test void jndiTemplate() {throw  new RuntimeException();}
}
