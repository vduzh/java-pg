package by.duzh.springframework.jdbc.datasource.init;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class DataSourceInitTestConfig {

    @Value("classpath:schema.sql")
    private Resource schemaScript;

    @Value("classpath:data.sql")
    private Resource dataScript;

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDatabasePopulator(databasePopulator());
        return initializer;
    }

    @Bean
    public DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(schemaScript, dataScript);
        return populator;
    }

    @Bean
    public DataSource dataSource() {
        String HSQLDB_DRIVER_NAME = "org.hsqldb.jdbc.JDBCDriver";

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(HSQLDB_DRIVER_NAME);
        dataSource.setUrl("jdbc:hsqldb:mem:mymemdb");
        dataSource.setUsername("sa");
        dataSource.setPassword("");

        return dataSource;
    }
}