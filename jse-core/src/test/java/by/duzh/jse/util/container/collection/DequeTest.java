package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

// double-ended queue
public class DequeTest {
    private Deque<Integer> deque;

    @Before
    public void init() {
        deque = new ArrayDeque<>(3);
    }

    @Test
    public void testAddFirst() {
        deque.addFirst(1);
        Assert.assertEquals(1, deque.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testAddFirstException() {
        //TODO: deque of limited length
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
    }

    @Test
    public void testAddLast() {
        deque.addLast(1);
        Assert.assertEquals(1, deque.size());
    }

    @Test
    public void testDescendingIterator() {
        deque.addFirst(1);
        deque.addFirst(2);
        Iterator<Integer> iterator = deque.descendingIterator();
    }

    @Test
    public void testGetFirst() {
        deque.addFirst(1);
        deque.addFirst(2);
        Assert.assertEquals(2, deque.getFirst().intValue());
    }

    @Test(expected= NoSuchElementException.class)
    public void testGetFirstError() {
        deque.getFirst();
    }

    @Test
    public void testGetLast() {
        deque.addFirst(1);
        deque.addFirst(2);
        Assert.assertEquals(1, deque.getLast().intValue());
    }

    @Test(expected= NoSuchElementException.class)
    public void testGetLastError() {
        deque.getLast();
    }

    @Test
    public void testOfferFirstOk() {
        Assert.assertTrue(deque.offerFirst(1));
    }

    @Test
    public void testOfferFirstNotOk() {
        //TODO: deque of limited length
        Assert.assertFalse(deque.offerFirst(1));
    }

    @Test
    public void testOfferLastOk() {
        Assert.assertTrue(deque.offerLast(1));
    }

    @Test
    public void testOfferLastNotOk() {
        //TODO: deque of limited length
        Assert.assertFalse(deque.offerLast(1));
    }

    @Test
    public void testPeekFirst() {
        Assert.assertTrue(deque.isEmpty());
        Assert.assertNull(deque.peekFirst());

        deque.offerFirst(1);
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.peekFirst().intValue());
    }

    @Test
    public void testPeekLast() {
        Assert.assertTrue(deque.isEmpty());
        Assert.assertNull(deque.peekLast());

        deque.offerFirst(1);
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.peekLast().intValue());
    }

    @Test
    public void testPollFirst() {
        Assert.assertTrue(deque.isEmpty());
        Assert.assertNull(deque.pollFirst());

        deque.offerFirst(1);
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.pollFirst().intValue());
        Assert.assertTrue(deque.isEmpty());
    }

    @Test
    public void testPollLast() {
        Assert.assertTrue(deque.isEmpty());
        Assert.assertNull(deque.pollLast());

        deque.offerFirst(1);
        Assert.assertFalse(deque.isEmpty());
        Assert.assertEquals(1, deque.pollLast().intValue());
        Assert.assertTrue(deque.isEmpty());
    }

    @Test
    public void testPop() {
        deque.offerFirst(1);
        deque.offerFirst(2);
        Assert.assertEquals(2, deque.pop().intValue());
        Assert.assertEquals(1, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testPopException() {
        deque.pop();
    }

    @Test
    public void testPush() {
        deque.push(1);
        deque.push(2);
        Assert.assertEquals(2, deque.getFirst().intValue());
    }

    @Test(expected = IllegalStateException.class)
    public void testPushError() {
        //TODO: deque of limited length
        deque.push(1);
    }

    @Test
    public void testRemoveFirst() {
        deque.offerFirst(1);
        deque.offerFirst(2);
        Assert.assertEquals(2, deque.removeFirst().intValue());
        Assert.assertEquals(1, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFirstException() {
        deque.removeFirst();
    }

    @Test
    public void testRemoveFirstOccurrence() {
        Assert.assertFalse(deque.removeFirstOccurrence(2));

        for (int i: new Integer[] {1, 2, 4, 2, 5}) {
            deque.offerFirst(i);
        }

        Assert.assertTrue(deque.removeFirstOccurrence(2));
        Assert.assertEquals(4, deque.size());
        for (int i: new Integer[] {5, 4, 2, 1}) {
            Assert.assertEquals(i, deque.poll().intValue());
        }
    }

    @Test
    public void testRemoveLast() {
        deque.offerLast(1);
        deque.offerLast(2);
        System.out.println(deque);
        Assert.assertEquals(2, deque.removeLast().intValue());
        Assert.assertEquals(1, deque.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastException() {
        deque.removeLast();
    }

    @Test
    public void testRemoveLastOccurrence() {
        Assert.assertFalse(deque.removeLastOccurrence(2));

        for (int i: new Integer[] {1, 2, 4, 2, 5}) {
            deque.offerLast(i);
        }

        Assert.assertTrue(deque.removeLastOccurrence(2));
        Assert.assertEquals(4, deque.size());
        for (int i: new Integer[] {1, 2, 4, 5}) {
            Assert.assertEquals(i, deque.poll().intValue());
        }
    }
}
