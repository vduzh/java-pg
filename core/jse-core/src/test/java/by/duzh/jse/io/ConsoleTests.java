package by.duzh.jse.io;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.io.Console;

// TODO: May be it is not necessary to test this system class
public class ConsoleTests {

    @Test
    public void testCreate() {
        Console console = System.console();
/*
        System.out.println(console);
        Assertions.assertNotNull(console);
*/
    }
}
