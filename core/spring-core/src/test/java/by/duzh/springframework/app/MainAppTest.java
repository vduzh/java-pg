package by.duzh.springframework.app;

import by.duzh.springframework.app.service.foo.FooService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAppTest {
    private AnnotationConfigApplicationContext ctx;

    @BeforeEach
    public void setUp() {
        ctx = new AnnotationConfigApplicationContext(
                AppConfig.class
        );
    }

    //@Autowired
    FooService fooService;

    @Test
    void run() {
        fooService = ctx.getBean(FooService.class);
        System.out.println(fooService.findById(1L));
    }

    @AfterEach
    public void tearDown() {
        if (ctx != null) {
            ctx.close();
        }
    }
}
