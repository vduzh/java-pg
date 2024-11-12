package by.duzh.springframework.springboot.core.metrics.buffering;

import by.duzh.springframework.springboot.DefaultSpringBootConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

public class BufferingApplicationStartupTest {

    @Test
    void name() {
        var ctx = new SpringApplicationBuilder()
                .parent(DefaultSpringBootConfiguration.class)
                .web(WebApplicationType.NONE)
                .applicationStartup(new BufferingApplicationStartup(2048))
                .run();

        var bean = ctx.getBean(BufferingApplicationStartup.class);
        bean.getBufferedTimeline().getEvents().forEach(e -> System.out.println(e.getStartupStep().getName()));
    }
}
