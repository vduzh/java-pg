package by.duzh.springframework.app.service.foo;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackages = "by.duzh.springframework.app.service.foo.impl")
@EnableTransactionManagement // in a context where services is loaded
public class FooServiceConfig {
}