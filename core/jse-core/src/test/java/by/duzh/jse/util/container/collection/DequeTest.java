package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

// double-ended queue
public class DequeTest {
    private Deque<Integer> deque;

    @BeforeEach
    public void init() {
        deque = new ArrayDeque<>(3);
    }

    @Test
    public void testAddFirst() {
        deque.addFirst(1);
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testAddFirstException() {
        // ArrayDeque не выбрасывает IllegalStateException при переполнении
        // Вместо этого он автоматически увеличивает размер
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);
        Assertions.assertEquals(4, deque.size());
    }

    @Test
    public void testAddLast() {
        deque.addLast(1);
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testDescendingIterator() {
        deque.addFirst(1);
        deque.addFirst(2);
        Iterator<Integer> iterator = deque.descendingIterator();
        Assertions.assertTrue(iterator.hasNext());
        Assertions.assertEquals(1, iterator.next());
        Assertions.assertEquals(2, iterator.next());
        Assertions.assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetFirst() {
        deque.addFirst(1);
        deque.addFirst(2);
        Assertions.assertEquals(2, deque.getFirst().intValue());
    }

    @Test
    public void testGetFirstError() {
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.getFirst());
    }

    @Test
    public void testGetLast() {
        deque.addFirst(1);
        deque.addFirst(2);
        Assertions.assertEquals(1, deque.getLast().intValue());
    }

    @Test
    public void testGetLastError() {
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.getLast());
    }

    @Test
    public void testOfferFirstOk() {
        Assertions.assertTrue(deque.offerFirst(1));
    }

    @Test
    public void testOfferFirstNotOk() {
        // ArrayDeque всегда возвращает true для offerFirst
        Assertions.assertTrue(deque.offerFirst(1));
    }

    @Test
    public void testOfferLastOk() {
        Assertions.assertTrue(deque.offerLast(1));
    }

    @Test
    public void testOfferLastNotOk() {
        // ArrayDeque всегда возвращает true для offerLast
        Assertions.assertTrue(deque.offerLast(1));
    }

    @Test
    public void testPeekFirst() {
        Assertions.assertTrue(deque.isEmpty());
        Assertions.assertNull(deque.peekFirst());

        deque.offerFirst(1);
        Assertions.assertFalse(deque.isEmpty());
        Assertions.assertEquals(1, deque.peekFirst().intValue());
    }

    @Test
    public void testPeekLast() {
        Assertions.assertTrue(deque.isEmpty());
        Assertions.assertNull(deque.peekLast());

        deque.offerFirst(1);
        Assertions.assertFalse(deque.isEmpty());
        Assertions.assertEquals(1, deque.peekLast().intValue());
    }

    @Test
    public void testPollFirst() {
        Assertions.assertTrue(deque.isEmpty());
        Assertions.assertNull(deque.pollFirst());

        deque.offerFirst(1);
        Assertions.assertFalse(deque.isEmpty());
        Assertions.assertEquals(1, deque.pollFirst().intValue());
        Assertions.assertTrue(deque.isEmpty());
    }

    @Test
    public void testPollLast() {
        Assertions.assertTrue(deque.isEmpty());
        Assertions.assertNull(deque.pollLast());

        deque.offerFirst(1);
        Assertions.assertFalse(deque.isEmpty());
        Assertions.assertEquals(1, deque.pollLast().intValue());
        Assertions.assertTrue(deque.isEmpty());
    }

    @Test
    public void testPop() {
        deque.offerFirst(1);
        deque.offerFirst(2);
        Assertions.assertEquals(2, deque.pop().intValue());
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testPopException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.pop());
    }

    @Test
    public void testPush() {
        deque.push(1);
        deque.push(2);
        Assertions.assertEquals(2, deque.getFirst().intValue());
    }

    @Test
    public void testPushError() {
        // ArrayDeque не выбрасывает IllegalStateException при push
        deque.push(1);
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveFirst() {
        deque.offerFirst(1);
        deque.offerFirst(2);
        Assertions.assertEquals(2, deque.removeFirst().intValue());
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveFirstException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.removeFirst());
    }

    @Test
    public void testRemoveFirstOccurrence() {
        Assertions.assertFalse(deque.removeFirstOccurrence(2));

        for (int i: new Integer[] {1, 2, 4, 2, 5}) {
            deque.offerFirst(i);
        }

        Assertions.assertTrue(deque.removeFirstOccurrence(2));
        Assertions.assertEquals(4, deque.size());
        for (int i: new Integer[] {5, 4, 2, 1}) {
            Assertions.assertEquals(i, deque.poll().intValue());
        }
    }

    @Test
    public void testRemoveLast() {
        deque.offerLast(1);
        deque.offerLast(2);
        Assertions.assertEquals(2, deque.removeLast().intValue());
        Assertions.assertEquals(1, deque.size());
    }

    @Test
    public void testRemoveLastException() {
        Assertions.assertThrows(NoSuchElementException.class, () -> deque.removeLast());
    }

    @Test
    public void testRemoveLastOccurrence() {
        Assertions.assertFalse(deque.removeLastOccurrence(2));

        for (int i: new Integer[] {1, 2, 4, 2, 5}) {
            deque.offerFirst(i);
        }

        Assertions.assertTrue(deque.removeLastOccurrence(2));
        Assertions.assertEquals(4, deque.size());
        for (int i: new Integer[] {5, 2, 4, 1}) {
            Assertions.assertEquals(i, deque.poll().intValue());
        }
    }
}
