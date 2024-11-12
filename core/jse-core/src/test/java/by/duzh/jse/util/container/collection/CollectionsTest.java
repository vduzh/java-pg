package by.duzh.jse.util.container.collection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Stream;

//TODO: Have a look into the source code of the Collections class
public class CollectionsTest {
    private Collection<Integer> collection;

    @Test
    public void testAddAll() {
        collection = new ArrayList<>();

        boolean res = Collections.addAll(collection, 1, 2, 3);

        Assert.assertTrue(res);
        Assert.assertEquals(collection.size(), 3);
    }

    @Test
    public void testAsLifoQueue() {
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addLast(3);
        deque.addLast(4);
        System.out.println(deque);

        Queue<Integer> queue = Collections.asLifoQueue(deque);
        System.out.println(queue);

        Assert.assertEquals(4, queue.poll().intValue());
    }

    @Test
    public void testBinarySearchWithComparator() {
        List<String> sortedList = Arrays.asList("four", "ONE", "three", "two", "X", "xy");
        Comparator<String> comparator = String::compareToIgnoreCase;

        Assert.assertEquals(1, Collections.binarySearch(sortedList, "one", comparator));
        Assert.assertEquals(4, Collections.binarySearch(sortedList, "x", comparator));
        Assert.assertTrue(Collections.binarySearch(sortedList, "Z", comparator) < 0);
    }

    @Test
    public void testBinarySearch() {
        List<Integer> sortedList = Arrays.asList(1, 3, 5, 7, 9);

        Assert.assertEquals(2, Collections.binarySearch(sortedList, 5));
        Assert.assertTrue(Collections.binarySearch(sortedList, 100) < 0);
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

        Assert.assertEquals(3, dest.size());
        Assert.assertEquals(2, dest.get(0).intValue());
        Assert.assertEquals(6, dest.get(1).intValue());
        Assert.assertEquals(5, dest.get(2).intValue());
    }

    @Test
    public void testDisjoint() {
        Assert.assertTrue(Collections.disjoint(Arrays.asList(1, 3, 5), Arrays.asList(2, 6)));
        Assert.assertTrue(Collections.disjoint(Arrays.asList(1, 3, 5), Collections.emptyList()));
        Assert.assertFalse(Collections.disjoint(Arrays.asList(1, 3, 5), Arrays.asList(2, 1)));
    }

    @Test
    public void testEmptyEnumeration() {
        Assert.assertFalse(Collections.<String>emptyEnumeration().hasMoreElements());
    }

    @Test
    public void testEmptyIterator() {
        Assert.assertFalse(Collections.<String>emptyIterator().hasNext());
    }

    @Test
    public void testEmptyListIterator() {
        Assert.assertFalse(Collections.<String>emptyListIterator().hasNext());
    }

    @Test
    public void testEmptyMap() {
        Assert.assertTrue(Collections.<Integer, String>emptyMap().isEmpty());
    }

    @Test
    public void testEmptyNavigableMap() {
        Assert.assertTrue(Collections.<Integer, String>emptyNavigableMap().isEmpty());
    }

    @Test
    public void testEmptyNavigableSet() {
        Assert.assertTrue(Collections.<Integer>emptyNavigableSet().isEmpty());
    }

    @Test
    public void testEmptySet() {
        Assert.assertTrue(Collections.<Integer>emptySet().isEmpty());
    }

    @Test
    public void testEmptySortedMap() {
        Assert.assertTrue(Collections.<Integer, String>emptySortedMap().isEmpty());
    }

    @Test
    public void testEmptySortedSet() {
        Assert.assertTrue(Collections.<Integer>emptySortedSet().isEmpty());
    }

    @Test
    public void testEnumerationFromCollection() {
        Enumeration<Integer> enumeration = Collections.<Integer>enumeration(Arrays.asList(1, 2));
        Assert.assertTrue(enumeration.hasMoreElements());
        Assert.assertEquals(1, enumeration.nextElement().intValue());
    }

    @Test
    public void testFillList() {
        List<Integer> list = Arrays.asList(1, 2, 3);

        Collections.fill(list, 7);

        for (int i : list) {
            Assert.assertEquals(7, i);
        }
    }

    @Test
    public void testFrequency() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 6, 2, 10);

        Assert.assertEquals(3, Collections.frequency(list, 2));
        Assert.assertEquals(1, Collections.frequency(list, 6));
        Assert.assertEquals(0, Collections.frequency(list, 123));
    }

    @Test
    public void testIndexOfSublist() {
        List<Integer> list = Arrays.asList(1, 2, 3, 2, 6, 2, 10);
        List<Integer> subList = Arrays.asList(2, 6, 2);

        // TODO: Look at the docs and source code
        Assert.assertEquals(3, Collections.indexOfSubList(list, subList));
        Assert.assertEquals(0, Collections.indexOfSubList(list, Collections.emptyList()));
        Assert.assertEquals(-1, Collections.indexOfSubList(list, Arrays.asList(23, 44)));
    }

    @Test
    public void testLastIndexOfSublist() {
        List<Integer> list = Arrays.asList(2, 6, 2, 1, 2, 3, 2, 6, 2, 10);
        List<Integer> subList = Arrays.asList(2, 6, 2);

        // TODO: Look at the docs and source code
        Assert.assertEquals(6, Collections.lastIndexOfSubList(list, subList));
        Assert.assertEquals(10, Collections.lastIndexOfSubList(list, Collections.emptyList()));
        Assert.assertEquals(-1, Collections.lastIndexOfSubList(list, Arrays.asList(23, 44)));
    }

    @Test
    public void testListFromEnumeration() {
        Enumeration<Integer> enumeration = new Vector<>(Arrays.asList(2, 6, 2)).elements();
        ArrayList<Integer> list = Collections.list(enumeration);

        Assert.assertEquals(3, list.size());
        Assert.assertEquals(2, list.get(0).intValue());
        Assert.assertEquals(6, list.get(1).intValue());
        Assert.assertEquals(2, list.get(2).intValue());
    }

    @Test
    public void testMaxWithComparator() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Collection<String> collection = Arrays.asList("1", "333", "22", "55555", "4444");

        Assert.assertEquals("55555", Collections.max(collection, comparator));
    }

    @Test
    public void testMax() {
        Collection<String> collection = Arrays.asList("A", "b", "c");

        Assert.assertEquals("c", Collections.max(collection));
    }

    @Test
    public void testMinWithComparator() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);

        Collection<String> collection = Arrays.asList("1", "333", "22", "55555", "4444");

        Assert.assertEquals("1", Collections.min(collection, comparator));
    }

    @Test
    public void testMin() {
        Collection<String> collection = Arrays.asList("A", "b", "c");

        Assert.assertEquals("A", Collections.min(collection));
    }

    @Test
    public void testNCopiesToList() {
        List<String> list = Collections.nCopies(10, "foo");

        Assert.assertEquals(10, list.size());
        Assert.assertEquals("foo", list.get(0));
        Assert.assertEquals("foo", list.get(9));
    }

    @Test
    public void testNewSetFromMap() {
        Set<String> set = Collections.newSetFromMap(Collections.<String, Boolean>emptyMap());

        Assert.assertTrue(set.isEmpty());
    }

    @Test
    public void testReplaceAllInList() {
        Assert.assertTrue(Collections.replaceAll(Arrays.asList(1, 2, 3, 2, 4), 2, 5));
        Assert.assertFalse(Collections.replaceAll(Arrays.asList(1, 3), 2, 5));
    }

    @Test
    public void testReversedOrder() {
        Comparator<String> comparator = Comparator.comparingInt(String::length);
        Comparator<String> newComparator = Collections.reverseOrder(comparator);

        Assert.assertTrue(newComparator.compare("1", "555") > 0);
        Assert.assertTrue(newComparator.compare("555", "1") < 0);
    }

    @Test
    public void testReversed() {
        Comparator<Integer> comparator = Collections.reverseOrder();

        Assert.assertTrue(comparator.compare(1, 5) > 0);
        Assert.assertTrue(comparator.compare(5, 1) < 0);
    }

    @Test
    public void testRotateList() {
        List<Integer> list = Arrays.asList(1, 2, 3, 5, 6);
        Collections.rotate(list, 1);

        Assert.assertTrue(list.containsAll(Arrays.asList(6, 1, 2, 3, 5)));

        Collections.rotate(list, -2);
        Assert.assertTrue(list.containsAll(Arrays.asList(2, 3, 5, 6, 1)));
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

        Assert.assertEquals(1, set.size());
        Assert.assertTrue(set.contains(1));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSingletonList() {
        List<Integer> list = Collections.singletonList(1);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).intValue());

        list.add(33);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testJDK9SingletonList() {
        List<Integer> list = List.of(1);

        Assert.assertEquals(1, list.size());
        Assert.assertEquals(1, list.get(0).intValue());

        list.add(33);
    }

    @Test
    public void testSingletonMap() {
        Map<Integer, String> map = Collections.singletonMap(1, "one");

        Assert.assertEquals(1, map.size());
        Assert.assertEquals("one", map.get(1));
    }

    @Test
    public void testSortList() {
        List<Integer> list = Arrays.asList(6, 1, 3, 5, 2);

        Collections.sort(list);

        Assert.assertEquals(0, Collections.indexOfSubList(Arrays.asList(1, 2, 3, 5, 6), list));
    }

    @Test
    public void testSortListWithComparator() {
        List<Integer> list = Arrays.asList(6, -1, 3, -5, 2);

        Collections.sort(list, Comparator.comparingInt(Math::abs));

        Assert.assertEquals(0, Collections.indexOfSubList(Arrays.asList(-1, 2, 3, -5, 6), list));
    }

    @Test
    public void testSwapList() {
        List<Integer> list = Arrays.asList(6, 1, 3, 5, 2);

        Collections.swap(list, 1, 3);

        Assert.assertEquals(0, Collections.indexOfSubList(Arrays.asList(6, 5, 3, 1, 2), list));
    }

    @Test
    public void testSynchronizedCollection() {
        Collection<Integer> syncCollection = Collections.synchronizedCollection(Arrays.asList(6, 1, 3, 5, 2));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableCollection() {
        Collection<Integer> unmodifiableCollection = Collections.unmodifiableCollection(Arrays.asList(6, 1, 3, 5, 2));
        unmodifiableCollection.add(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnmodifiableList() {
        List<Integer> src = Arrays.asList(6, 1, 3, 5, 2);
        List<Integer> unmodifiableList = Collections.unmodifiableList(src);
        unmodifiableList.add(0);
    }

    @Test
    public void testUnmodifiableListParadox() {
        List<Integer> src = new ArrayList<>(Arrays.asList(2, 1));
        List<Integer> unmodifiableList = Collections.unmodifiableList(src);
        src.add(3);
        Assert.assertEquals(3, unmodifiableList.size()); // unmodifiableList is modifiable here!!!
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEMPTY_LIST() {
        List<String> list = Collections.EMPTY_LIST;
        list.add("1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testJDK9EMPTY_LIST() {
        List<String> list = List.of();
        list.add("1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEMPTY_SET() {
        Set<String> set = Collections.EMPTY_SET;
        set.add("one");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testEMPTY_MAP() {
        Map<Integer, String> map = Collections.EMPTY_MAP;
        map.put(1, "one");
    }
}