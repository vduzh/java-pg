package by.duzh.jse;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * It is a new type
 */
enum Operation {
    // it is an identifier which are the constants of the enum
    CREATE, READ, UPDATE, DELETE;
}


public class EnumTests {
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
        Assert.assertTrue(op instanceof Enum);

        Assert.assertEquals(1, op.ordinal());
        Assert.assertEquals(3, Operation.DELETE.ordinal());
        Assert.assertFalse(Operation.DELETE.compareTo(Operation.READ) < 0);

    }

    @Test
    public void asString() {
        Assert.assertEquals("READ", Operation.READ.toString());
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
        Assert.assertEquals(Operation.READ, op);
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
        Assert.assertEquals("dva", foo.getTranslation());
    }
}
