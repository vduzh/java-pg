package by.duzh.jse.oop;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Box {
    int width;
    int height;
    int depth;

    Box() {
    }

    Box(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    Box(int value) {
        this.width = value;
        this.height = value;
        this.depth = value;

    }

    Box(Box box) {
        this.width = box.width;
        this.height = box.height;
        this.depth = box.depth;
    }


    int getVolume() {
        return this.width * height * depth;
    }

    void voidMethod() {
    }

    int parametrizedMethod(int i) {
        return i * i;
    }

    int unlimitedLength(int... numbers) {
        int res = 0;
        for (int i : numbers) {
            res += i;
        }
        return res;
    }


    void setWidth(int width) {
        this.width = width;
    }

    int calculate(int i) {
        return i * 2;
    }

    int calculate() {
        return 7;
    }

    // a variable-length argument
    int sum(int... nums) {
        return Arrays.stream(nums).sum();
    }

    static int staticMethod() {
        return 6;
    }

    /**
     * Overloading (object methods)
     */
    long calculate(long l) {
        return l * 10;
    }

    /**
     * Overloading (class/static method)
     */
    static int staticMethod(int value) {
        return value;
    }

    /**
     * Overloading a variable-length argument method
     */
    long sum(long... nums) {
        return Arrays.stream(nums).sum();
    }

    long sum(long start, int... nums) {
        return start + Arrays.stream(nums).sum();
    }

    /**
     * Modifiers
     */
    // public level
    public int publicValue;
    // class level
    private int privateValue;
    // package level
    int defaultValue;
    // package level + all the child classed
    protected int protectedValue;

    public int getPrivateValue() {
        return privateValue;
    }

    public void setPrivateValue(int value) {
        this.privateValue = value;
    }

    /**
     * Final
     */
    final int finalValue = 1;

}

public class BasicTests {
    @Test
    public void testCreate() {
        // declare var
        Box some;

        // declare and init with the default constructor
        Box box = new Box();

        // create an object
        Box second = new Box(2, 3, 4);
        assertEquals(2, second.width);
        assertEquals(3, second.height);
        assertEquals(4, second.depth);

        // create a box with the side
        Box third = new Box(8);
        assertEquals(8, third.width);
        assertEquals(8, third.height);
        assertEquals(8, third.depth);

        // create an object from another object
        Box fourth = new Box(1, 3, 5);
        assertEquals(1, fourth.width);
        assertEquals(3, fourth.height);
        assertEquals(5, fourth.depth);

    }

    @Test
    public void testInit() {
        Box box = new Box();
        box.width = 10;
    }

    @Test
    public void testFields() {
        Box box = new Box();
        assertEquals(0, box.width);
    }

    @Test
    public void testMethods() {
        Box box = new Box();

        box.voidMethod();
        assertEquals(0, box.getVolume());
        assertEquals(100, box.parametrizedMethod(10));

        box.setWidth(5);
        assertEquals(5, box.width);
    }

    @Test
    public void testUnlimitedLength() {
        Box box = new Box();
        assertEquals(6, box.unlimitedLength(1, 2, 3));
        assertEquals(1, box.unlimitedLength(1));
        assertEquals(0, box.unlimitedLength());
    }

    @Test
    public void testStaticMethods() {
        int res = Box.staticMethod();
        assertEquals(6, res);
    }

    @Test
    public void testOverloading() {
        // object methods
        Box box = new Box();
        assertEquals(14, box.calculate(7));
        assertEquals(7, box.calculate());
        assertEquals(70, box.calculate(7L));

        // class methods
        assertEquals(6, Box.staticMethod());
        assertEquals(7, Box.staticMethod(7));

        // variable-length argument
        assertEquals(6, box.sum(1, 2, 3));
        assertEquals(6, box.sum(1L, 2L, 3L));
        assertEquals(7, box.sum(1, 2, 3, 1));
    }

    @Test
    public void testPrivateMember() {
        Box box = new Box();
        box.setPrivateValue(10);
        assertEquals(10, box.getPrivateValue());
    }

    @Test
    public void testPublicMember() {
        Box box = new Box();
        box.publicValue = 10;
        assertEquals(10, box.publicValue);
    }

    @Test
    public void testDefaultMember() {
        Box box = new Box();
        box.defaultValue = 10;
        assertEquals(10, box.defaultValue);
    }

    @Test
    public void testProtectedMember() {
        Box box = new Box();
        box.protectedValue = 10;
        assertEquals(10, box.protectedValue);
    }

    @Test
    public void testFinalSpecifier() {
        Box box = new Box();
        assertEquals(1, box.finalValue);
    }
}
