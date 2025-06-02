package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

public class StackTest {
    private Stack<Integer> stack;

    @BeforeEach
    public void setUp() {
        stack = new Stack<>();
    }

    @Test
    public void testEmpty() {
        Assertions.assertTrue(stack.empty());

        stack.add(1);

        Assertions.assertFalse(stack.empty());
    }

    @Test
    public void testPush() {
        int i = stack.push(1);

        Assertions.assertFalse(stack.empty());
        Assertions.assertEquals(1, i);
    }

    @Test
    public void testPeek() {
        stack.push(1);

        int i = stack.peek();

        Assertions.assertFalse(stack.empty());
        Assertions.assertEquals(1, i);
    }

    @Test
    public void testPeekOnEmptyStack() {
        Assertions.assertThrows(EmptyStackException.class, () -> stack.peek());
    }

    @Test
    public void testPop() {
        stack.push(1);

        int i = stack.pop();

        Assertions.assertTrue(stack.empty());
        Assertions.assertEquals(1, i);
    }

    @Test
    public void testPopOnEmptyStack() {
        Assertions.assertThrows(EmptyStackException.class, () -> stack.pop());
    }

    @Test
    public void testSearch() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        int i = stack.search(3);
        int j = stack.search(99);

        Assertions.assertEquals(3, i);
        Assertions.assertEquals(-1, j);
    }
}