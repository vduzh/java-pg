package by.duzh.jse.util.container.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

//TODO: Have a look into the source code of the Collections class
public class CollectionsTest {
    private Collection<Integer> collection;

    @BeforeEach
    public void init() {
        // если был @Before, теперь @BeforeEach
    }

    @Test
    public void testAddAll() {
        collection = new ArrayList<>();

        boolean res = Collections.addAll(collection, 1, 2, 3);

        Assertions.assertTrue(res);
        Assertions.assertEquals(collection.size(), 3);
    }

    @Test
    public void testAsLifoQueue() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        deque.addFirst(4);

        Queue<Integer> queue = Collections.asLifoQueue(deque);
        
        // LIFO (Last In First Out) - последний добавленный элемент выходит первым
        Assertions.assertEquals(4, queue.poll().intValue());
        Assertions.assertEquals(3, queue.poll().intValue());
        Assertions.assertEquals(2, queue.poll().intValue());
        Assertions.assertEquals(1, queue.poll().intValue());
        Assertions.assertNull(queue.poll());
    }

    @Test
    public void testBinarySearchWithComparator() {
        List<String> sortedList = Arrays.asList("four", "ONE", "three", "two", "X", "xy");
        Comparator<String> comparator = String::compareToIgnoreCase;

        Assertions.assertEquals(1, Collections.binarySearch(sortedList, "one", comparator));
        Assertions.assertEquals(4, Collections.binarySearch(sortedList, "x", comparator));
        Assertions.assertTrue(Collections.binarySearch(sortedList, "Z", comparator) < 0);
    }

    @Test
    public void testBinarySearch() {
        List<Integer> sortedList = Arrays.asList(1, 3, 5, 7, 9);

        Assertions.assertEquals(2, Collections.binarySearch(sortedList, 5));
        Assertions.assertTrue(Collections.binarySearch(sortedList, 100) < 0);
    }

    @Test
    public void testCheckedCollection() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedCollection()
    }

    @Test
    public void testCheckedList() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedList()
    }

    @Test
    public void testCheckedMap() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedMap()
    }

    @Test
    public void testCheckedNavigableMap() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedNavigableMap()
    }

    @Test
    public void testCheckedNavigableSet() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedNavigableSet()
    }

    @Test
    public void testCheckedQueue() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedQueue()
    }

    @Test
    public void testCheckedSet() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedSet()
    }

    @Test
    public void testCheckedSortedMap() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedSortedMap()
    }

    @Test
    public void testCheckedSortedSet() {
        // TODO: not clear
        // Have a look at the source code
        //Collections.checkedSortedSet()
    }

    @Test
    public void testCopyLists() {
        List<Integer> dest = Arrays.asList(1, 3, 5);
        List<Integer> src = Arrays.asList(2, 6);

        Collections.copy(dest, src);

        Assertions.assertEquals(3, dest.size());
        Assertions.assertEquals(2, dest.get(0).intValue());
        Assertions.assertEquals(6, dest.get(1).intValue());
        Assertions.assertEquals(5, dest.get(2).intValue());
    }

    @Test
    public void testDisjoint() {
        Assertions.assertTrue(Collections.disjoint(Arrays.asList(1, 3, 5), Arrays.asList(2, 6)));
        Assertions.assertTrue(Collections.disjoint(Arrays.asList(1, 3, 5), Collections.emptyList()));
        Assertions.assertFalse(Collections.disjoint(Arrays.asList(1, 3, 5), Arrays.asList(2, 1)));
    }

    @Test
    public void testEmptyEnumeration() {
        Assertions.assertFalse(Collections.<String>emptyEnumeration().hasMoreElements());
    }

    @Test
    public void testEmptyIterator() {
        Assertions.assertFalse(Collections.<String>emptyIterator().hasNext());
    }

    @Test
    public void testEmptyListIterator() {
        Assertions.assertFalse(Collections.<String>emptyListIterator().hasNext());
    }

    @Test
    public void testEmptyMap() {
        Assertions.assertTrue(Collections.<Integer, String>emptyMap().isEmpty());
    }

    @Test
    public void testEmptyNavigableMap() {
        Assertions.assertTrue(Collections.<Integer, String>emptyNavigableMap().isEmpty());
    }

    @Test
    public void testEmptyNavigableSet() {
        Assertions.assertTrue(Collections.<Integer>emptyNavigableSet().isEmpty());
    }

    @Test
    public void testEmptySet() {
        Assertions.assertTrue(Collections.<Integer>emptySet().isEmpty());
    }

    @Test
    public void testEmptySortedMap() {
        Assertions.assertTrue(Collections.<Integer, String>emptySortedMap().isEmpty());
    }

    @Test
    public void testEmptySortedSet() {
        Assertions.assertTrue(Collections.<Integer>emptySortedSet().isEmpty());
    }

    @Test
    public void testEnumerationFromCollection() {
        Enumeration<Integer> enumeration = Collections.<Integer>enumeration(Arrays.asList(1, 2));
        Assertions.assertTrue(enumeration.hasMoreElements());
        Assertions.assertEquals(1, enumeration.nextElement().intValue());
    }

    @Test
    public void testFillList() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        Collections.fill(list, 7);

        for (int i : list) {
            Assertions.assertEquals(7, i);
        }
    }

    @Test
    public void testFrequency() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 6, 2, 10);

        Assertions.assertEquals(3, Collections.frequency(list, 2));
        Assertions.assertEquals(1, Collections.frequency(list, 6));
        Assertions.assertEquals(0, Collections.frequency(list, 123));
    }

    @Test
    public void testIndexOfSublist() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 6, 2, 10);
        List<Integer> subList = Arrays.asList(2, 6, 2);

        // TODO: Look at the docs and source code
        Assertions.assertEquals(3, Collections.indexOfSubList(list, subList));
        Assertions.assertEquals(0, Collections.indexOfSubList(list, Collections.emptyList()));
        Assertions.assertEquals(-1, Collections.indexOfSubList(list, Arrays.asList(23, 44)));
    }

    @Test
    public void testLastIndexOfSublist() {
        List<Integer> list = Arrays.asList(2, 6, 2, 1, 2, 3, 2, 6, 2, 10);
        List<Integer> subList = Arrays.asList(2, 6, 2);

        // TODO: Look at the docs and source code
        Assertions.assertEquals(6, Collections.lastIndexOfSubList(list, subList));
        Assertions.assertEquals(10, Collections.lastIndexOfSubList(list, Collections.emptyList()));
        Assertions.assertEquals(-1, Collections.lastIndexOfSubList(list, Arrays.asList(23, 44)));
    }

    @Test
    public void testListFromEnumeration() {
        Enumeration<Integer> enumeration = new Vector<>(Arrays.asList(2, 6, 2)).elements();
        ArrayList<Integer> list = Collections.list(enumeration);

        Assertions.assertEquals(3, list.size());
        Assertions.assertEquals(2, list.get(0).intValue());
        Assertions.assertEquals(6, list.get(1).intValue());
        Assertions.assertEquals(2, list.get(2).intValue());
    }

    @Test
    public void testMaxWithComparator() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Collection<String> collection = Arrays.asList("1", "333", "22", "55555", "4444");

        Assertions.assertEquals("55555", Collections.max(collection, comparator));
    }

    @Test
    public void testMax() {
        Collection<String> collection = Arrays.asList("A", "b", "c");

        Assertions.assertEquals("c", Collections.max(collection));
    }

    @Test
    public void testMinWithComparator() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Collection<String> collection = Arrays.asList("1", "333", "22", "55555", "4444");

        Assertions.assertEquals("1", Collections.min(collection, comparator));
    }

    @Test
    public void testMin() {
        Collection<String> collection = Arrays.asList("A", "b", "c");

        Assertions.assertEquals("A", Collections.min(collection));
    }

    @Test
    public void testNCopiesToList() {
        List<String> list = Collections.nCopies(10, "foo");

        Assertions.assertEquals(10, list.size());
        Assertions.assertEquals("foo", list.get(0));
        Assertions.assertEquals("foo", list.get(9));
    }

    @Test
    public void testNewSetFromMap() {
        Set<String> set = Collections.newSetFromMap(Collections.<String, Boolean>emptyMap());

        Assertions.assertTrue(set.isEmpty());
    }

    @Test
    public void testReplaceAllInList() {
        Assertions.assertTrue(Collections.replaceAll(Arrays.asList(1, 2, 3, 2, 4), 2, 5));
        Assertions.assertFalse(Collections.replaceAll(Arrays.asList(1, 3), 2, 5));
    }

    @Test
    public void testReversedOrder() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        Comparator<String> newComparator = Collections.reverseOrder(comparator);

        Assertions.assertTrue(newComparator.compare("1", "555") > 0);
        Assertions.assertTrue(newComparator.compare("555", "1") < 0);
    }

    @Test
    public void testReversed() {
        Comparator<Integer> comparator = Collections.reverseOrder();

        Assertions.assertTrue(comparator.compare(1, 5) > 0);
        Assertions.assertTrue(comparator.compare(5, 1) < 0);
    }

    @Test
    public void testRotateList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6);
        Collections.rotate(list, 1);

        Assertions.assertTrue(list.containsAll(Arrays.asList(6, 1, 2, 3, 5)));

        Collections.rotate(list, -2);
        Assertions.assertTrue(list.containsAll(Arrays.asList(2, 3, 5, 6, 1)));
    }

    @Test
    public void testShuffleListWithRandom() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6);
        Collections.shuffle(list, new Random());
    }

    @Test
    public void testShuffleList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6);
        Collections.shuffle(list);
    }

    @Test
    public void testSingletonSet() {
        Set<Integer> set = Collections.singleton(1);

        Assertions.assertEquals(1, set.size());
        Assertions.assertTrue(set.contains(1));
    }

    @Test
    public void testSingletonList() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.singletonList(1).add(2);
        });
    }

    @Test
    public void testJDK9SingletonList() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            List.of(1).add(2);
        });
    }

    @Test
    public void testSingletonMap() {
        Map<Integer, String> map = Collections.singletonMap(1, "one");

        Assertions.assertEquals(1, map.size());
        Assertions.assertEquals("one", map.get(1));
    }

    @Test
    public void testSortList() {
        List<Integer> list = Arrays.asList(6, 1, 3, 5, 2);

        Collections.sort(list);

        Assertions.assertEquals(0, Collections.indexOfSubList(Arrays.asList(1, 2, 3, 5, 6), list));
    }

    @Test
    public void testSortListWithComparator() {
        List<Integer> list = Arrays.asList(6, -1, 3, -5, 2);

        Collections.sort(list, Comparator.comparingInt(Math::abs));

        Assertions.assertEquals(0, Collections.indexOfSubList(Arrays.asList(-1, 2, 3, -5, 6), list));
    }

    @Test
    public void testSwapList() {
        List<Integer> list = Arrays.asList(6, 1, 3, 5, 2);

        Collections.swap(list, 1, 3);

        Assertions.assertEquals(0, Collections.indexOfSubList(Arrays.asList(6, 5, 3, 1, 2), list));
    }

    @Test
    public void testSynchronizedCollection() {
        Collection<Integer> syncCollection = Collections.synchronizedCollection(Arrays.asList(6, 1, 3, 5, 2));
    }

    @Test
    public void testUnmodifiableCollection() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.unmodifiableCollection(new ArrayList<>()).add(1);
        });
    }

    @Test
    public void testUnmodifiableList() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.unmodifiableList(new ArrayList<>()).add(1);
        });
    }

    @Test
    public void testUnmodifiableListParadox() {
        List<Integer> src = new ArrayList<>(Arrays.asList(2, 1));
        List<Integer> unmodifiableList = Collections.unmodifiableList(src);
        src.add(3);
        Assertions.assertEquals(3, unmodifiableList.size()); // unmodifiableList is modifiable here!!!
    }

    @Test
    public void testEMPTY_LIST() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.EMPTY_LIST.add(1);
        });
    }

    @Test
    public void testJDK9EMPTY_LIST() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            List.of().add(1);
        });
    }

    @Test
    public void testEMPTY_SET() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.EMPTY_SET.add(1);
        });
    }

    @Test
    public void testEMPTY_MAP() {
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            Collections.EMPTY_MAP.put(1, 2);
        });
    }
}