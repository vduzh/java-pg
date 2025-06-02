package by.duzh.jse.util.container.array;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.IntStream;

// TODO: Have a look at the source code
// TODO: Have a look at its usage in ArrayList
public class ArraysTest {

    @Test
    public void testAsList() {
        List<Integer> list = Arrays.asList(1, 2, 3);
    }

    @Test
    public void testBinarySearchInt() {
        int[] arr = {1, 2, 3};

        Assertions.assertEquals(1, Arrays.binarySearch(arr, 2));
        Assertions.assertTrue(Arrays.binarySearch(arr, 22) < 0);
    }

    @Test
    public void testBinarySearchString() {
        String[] arr = {"a", "b", "c", "d", "e"};

        Assertions.assertEquals(3, Arrays.binarySearch(arr, "d"));
        Assertions.assertTrue(Arrays.binarySearch(arr, "22") < 0);
    }

    @Test
    public void testBinaryCopyOfArray() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String[] copy = Arrays.copyOf(arr, 10);

        Assertions.assertEquals(10, copy.length);
        Assertions.assertEquals("b", copy[1]);
        Assertions.assertNull(copy[5]);

        copy = Arrays.copyOf(arr, 3);

        Assertions.assertEquals(3, copy.length);
        Assertions.assertEquals("c", copy[2]);
    }

    @Test
    public void testBinaryCopyOfRange() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String[] copy = Arrays.copyOfRange(arr, 1, 4);

        Assertions.assertEquals(3, copy.length);
        Assertions.assertEquals("b", copy[0]);
        Assertions.assertEquals("c", copy[1]);
        Assertions.assertEquals("d", copy[2]);
    }

    @Test
    public void testJDK9Compare() {
        // the whole arrays
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "b"};
        Assertions.assertEquals(0, Arrays.compare(arr1, arr2));

        arr1 = new String[]{"a"};
        Assertions.assertTrue(Arrays.compare(arr1, arr2) < 0);

        arr1 = new String[]{"a", "v"};
        Assertions.assertTrue(Arrays.compare(arr1, arr2) > 0);

        // subarray
        arr1 = new String[] {"1", "a", "b", "2"};
        arr2 = new String[] {"1", "1", "a", "b", "2", "2"};
        Assertions.assertEquals(0, Arrays.compare(arr1, 1, 3, arr2, 2, 4));
    }

    @Test
    public void testJDK9Mismatch() {
        // the whole arrays
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "b"};
        Assertions.assertEquals(-1, Arrays.mismatch(arr1, arr2));

        arr1 = new String[]{"a", "b", "c"};
        Assertions.assertEquals(2, Arrays.mismatch(arr1, arr2));

        // subarray
        arr1 = new String[] {"1", "a", "b", "2"};
        arr2 = new String[] {"1", "1", "a", "b", "2", "2"};
        Assertions.assertEquals(2, Arrays.mismatch(arr1, 1, 3, arr2, 2, 5));
    }

    @Test
    public void testEquals() {
        String[] arr = {"a", "b", "c", "d", "e"};
        String[] arr2 = {"a", "b"};
        String[] arr3 = {"b", "a", "c", "d", "e"};
        String[] arr4 = {"a", "b", "c", "d", "e"};

        Assertions.assertFalse(Arrays.equals(arr, arr2));
        Assertions.assertFalse(Arrays.equals(arr, arr3));
        Assertions.assertTrue(Arrays.equals(arr, arr4));

        // JDK9
        Assertions.assertTrue(Arrays.equals(arr, 2, 4, arr4, 2, 4));
    }

    @Test
    public void testDeepEqualsStringArrays() {
        String[] arr = {"a", "b", "c", "d", "e"};
        String[] arr2 = {"a", "b"};
        String[] arr3 = {"b", "a", "c", "d", "e"};
        String[] arr4 = {"a", "b", "c", "d", "e"};

        Assertions.assertFalse(Arrays.deepEquals(arr, arr2));

        // TODO: test arrays
    }

    @Test
    public void testJDK9Equals() {

    }

    @Test
    public void testFill() {
        String[] arr = new String[10];

        Arrays.fill(arr, "foo");

        Assertions.assertEquals("foo", arr[0]);
        Assertions.assertEquals("foo", arr[9]);
    }

    @Test
    public void testFillRange() {
        String[] arr = new String[10];

        Arrays.fill(arr, 3, 6, "foo");

        Assertions.assertNull(arr[2]);
        Assertions.assertEquals("foo", arr[3]);
        Assertions.assertEquals("foo", arr[5]);
        Assertions.assertNull(arr[6]);
    }

    @Test
    public void testSort() {
        String[] arr = {"b", "a", "e", "c", "d"};

        Arrays.sort(arr);

        Assertions.assertEquals("a", arr[0]);
        Assertions.assertEquals("e", arr[4]);
    }

    @Test
    public void testSortWithComparator() {
        String[] arr = {"bbb", "aa", "e", "cccc", "ddddd"};

        Arrays.sort(arr, Comparator.comparingInt(String::length));

        Assertions.assertEquals("e", arr[0]);
        Assertions.assertEquals("aa", arr[1]);
        Assertions.assertEquals("bbb", arr[2]);
        Assertions.assertEquals("cccc", arr[3]);
        Assertions.assertEquals("ddddd", arr[4]);
    }

    @Test
    public void testParallelSort() {
        String[] arr = {"b", "a", "e", "c", "d"};

        Arrays.parallelSort(arr);

        Assertions.assertEquals("a", arr[0]);
        Assertions.assertEquals("e", arr[4]);
    }

    @Test
    public void testParallelSortWithComparator() {
        String[] arr = {"bbb", "aa", "e", "cccc", "ddddd"};

        Arrays.parallelSort(arr, Comparator.comparingInt(String::length));

        Assertions.assertEquals("e", arr[0]);
        Assertions.assertEquals("aa", arr[1]);
        Assertions.assertEquals("bbb", arr[2]);
        Assertions.assertEquals("cccc", arr[3]);
        Assertions.assertEquals("ddddd", arr[4]);
    }

    // TODO: write test - have a look at spliterator
    @Test
    public void testSpliterator() {
        String[] arr = {"a", "b", "c", "d", "e"};

        Spliterator<String> spliterator = Arrays.spliterator(arr);

        Assertions.assertEquals(5, spliterator.estimateSize());
    }

    @Test
    public void testStream() {
        String[] arr = {"a", "b", "c", "d", "e"};

        long count = Arrays.stream(arr).count();

        Assertions.assertEquals(5, count);
    }

    @Test
    public void testStreamWithRange() {
        String[] arr = {"a", "b", "c", "d", "e"};

        long count = Arrays.stream(arr, 1, 4).count();

        Assertions.assertEquals(3, count);
    }

    @Test
    public void testSetAll() {
        String[] arr = new String[5];

        Arrays.setAll(arr, i -> String.valueOf(i));

        Assertions.assertEquals("0", arr[0]);
        Assertions.assertEquals("4", arr[4]);
    }

    @Test
    public void testParallelSetAll() {
        String[] arr = new String[5];

        Arrays.parallelSetAll(arr, i -> String.valueOf(i));

        Assertions.assertEquals("0", arr[0]);
        Assertions.assertEquals("4", arr[4]);
    }

    @Test
    public void testParallelPrefix() {
        int[] arr = {1, 2, 3, 4, 5};

        Arrays.parallelPrefix(arr, (a, b) -> a + b);

        Assertions.assertEquals(1, arr[0]);
        Assertions.assertEquals(3, arr[1]);
        Assertions.assertEquals(6, arr[2]);
        Assertions.assertEquals(10, arr[3]);
        Assertions.assertEquals(15, arr[4]);
    }

    @Test
    public void testParallelPrefixWithStrings() {
        String[] arr = {"a", "b", "c", "d", "e"};

        Arrays.parallelPrefix(arr, (a, b) -> a + b);

        Assertions.assertEquals("a", arr[0]);
        Assertions.assertEquals("ab", arr[1]);
        Assertions.assertEquals("abc", arr[2]);
        Assertions.assertEquals("abcd", arr[3]);
        Assertions.assertEquals("abcde", arr[4]);
    }

    @Test
    public void testToString() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String s = Arrays.toString(arr);

        Assertions.assertEquals("[a, b, c, d, e]", s);
    }

    @Test
    public void testHashCode() {
        String[] arr1 = {"a", "b", "c", "d", "e"};
        String[] arr2 = {"a", "b", "c", "d", "e"};
        String[] arr3 = {"a", "b", "c", "d", "f"};

        Assertions.assertEquals(Arrays.hashCode(arr1), Arrays.hashCode(arr2));
        Assertions.assertNotEquals(Arrays.hashCode(arr1), Arrays.hashCode(arr3));
    }

    @Test
    public void testDeepToString() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String s = Arrays.deepToString(arr);

        Assertions.assertEquals("[a, b, c, d, e]", s);
    }
}
