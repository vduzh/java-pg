package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;

public class StackTest {
    private Stack<Integer> stack;

    @Before
    public void setUp() {
        stack = new Stack<>();
    }

    @Test
    public void testEmpty() {
        Assert.assertTrue(stack.empty());

        stack.add(1);

        Assert.assertFalse(stack.empty());
    }

    @Test
    public void testPush() {
        int i = stack.push(1);

        Assert.assertFalse(stack.empty());
        Assert.assertEquals(1, i);
    }

    @Test
    public void testPeek() {
        stack.push(1);

        int i = stack.peek();

        Assert.assertFalse(stack.empty());
        Assert.assertEquals(1, i);
    }

    @Test(expected = EmptyStackException.class)
    public void testPeekOnEmptyStack() {
        stack.peek();
    }

    @Test
    public void testPop() {
        stack.push(1);

        int i = stack.pop();

        Assert.assertTrue(stack.empty());
        Assert.assertEquals(1, i);
    }

    @Test(expected = EmptyStackException.class)
    public void testPopOnEmptyStack() {
        stack.pop();
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

        Assert.assertEquals(3, i);
        Assert.assertEquals(-1, j);
    }
}