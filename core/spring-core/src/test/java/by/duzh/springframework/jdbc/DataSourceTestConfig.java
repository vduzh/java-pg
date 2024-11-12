package by.duzh.springframework.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassRelativeResourceLoader;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import javax.sql.DataSource;

@Configuration
public class DataSourceTestConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder(new ClassRelativeResourceLoader(this.getClass()))
                .generateUniqueName(true)
                .setScriptEncoding("UTF-8")
                .addDefaultScripts()
                .build();
    }
}