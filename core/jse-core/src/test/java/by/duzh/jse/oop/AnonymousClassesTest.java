package by.duzh.jse.oop;

import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;

public class AnonymousClassesTest {

    @Test
    public void testCreateWithAngleBrackets() {
        Callable<Integer> callable = new Callable<>() {
            @Override
            public Integer call() throws Exception {
                return 123;
            }
        };
    }
}
