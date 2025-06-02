package by.duzh.jse;

import org.junit.jupiter.api.Test;

class Application {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println("Argument " + i + ": " + args[i]);
        }
    }
}

public class CommandLineArgsTests {
    @Test
    public void testArguments() throws Exception {
        Application.main(new String[]{"foo", "bar"});
    }
}
