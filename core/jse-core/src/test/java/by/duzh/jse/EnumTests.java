package by.duzh.jse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.logging.Logger;
import java.util.Random;

/**
 * It is a new type
 */
enum Operation {
    // it is an identifier which are the constants of the enum
    CREATE, READ, UPDATE, DELETE;
}

/**
 * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Enum.html">Enum</a>
 */
public class EnumTests {
    private static final Logger logger = Logger.getLogger(EnumTests.class.getName());

    private Operation randomOperation() {
        var operations = Operation.values();
        return operations[new Random().nextInt(operations.length)];
    }


    @Test
    public void createAndInitVar() {
        Operation op = Operation.READ;
    }

    @Test
    public void itIsEnum() {
        Operation op = Operation.READ;
        Assertions.assertTrue(op instanceof Enum);

        Assertions.assertEquals(1, op.ordinal());
        Assertions.assertEquals(3, Operation.DELETE.ordinal());
        Assertions.assertFalse(Operation.DELETE.compareTo(Operation.READ) < 0);

    }

    @Test
    public void asString() {
        Assertions.assertEquals("READ", Operation.READ.toString());
    }

    @Test
    public void compare() {
        Operation one = randomOperation();
        Operation two = randomOperation();

        // compare
        boolean res = one == two;
        res = one != two;
    }

    @Test
    public void testEquals() {
        Operation one = randomOperation();
        Operation two = randomOperation();
        boolean res = one.equals(two);
    }


    @Test
    public void useInSwitch() {
        Operation op = randomOperation();

        switch (op) {
            case READ:
                System.out.println("read");
                break;
            case UPDATE:
                System.out.println("update");
                break;
        }
    }

    @Test
    public void testValues() {
        Operation[] values = Operation.values();
        System.out.println(values);
    }

    @Test
    public void testValueOf() {
        Operation op = Operation.valueOf("READ");
        Assertions.assertEquals(Operation.READ, op);
    }

    @Test
    public void loop() {
        for (Operation op : Operation.values()) {
            System.out.println(op);
        }
    }

    @Test
    public void isClass() {
        // declare enum as a class
        enum Foo {
            ONE("odin"), TWO("dva");

            private final String translation;

            // create a constructor
            Foo(String translation) {
                this.translation = translation;
            }

            public String getTranslation() {
                return translation;
            }
        }

        // create enum object
        Foo foo = Foo.TWO;

        // do assertion
        Assertions.assertEquals("dva", foo.getTranslation());
    }
}
