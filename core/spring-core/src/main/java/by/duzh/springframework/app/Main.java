package by.duzh.springframework.app;

import by.duzh.springframework.app.service.foo.FooService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        //String[] profiles = {"jpa"};
        String[] profiles = {"hibernate"};
        //String[] profiles = {"jdbc"};

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles(profiles);
        ctx.register(AppConfig.class);
        ctx.refresh();

        FooService fooService = ctx.getBean(FooService.class);
        System.out.println(fooService.findById(1L));
        ctx.close();
    }
}
