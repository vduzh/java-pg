package by.duzh.jse.util.container.array;

import org.junit.Assert;
import org.junit.Test;

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

        Assert.assertEquals(1, Arrays.binarySearch(arr, 2));
        Assert.assertTrue(Arrays.binarySearch(arr, 22) < 0);
    }

    @Test
    public void testBinarySearchString() {
        String[] arr = {"a", "b", "c", "d", "e"};

        Assert.assertEquals(3, Arrays.binarySearch(arr, "d"));
        Assert.assertTrue(Arrays.binarySearch(arr, "22") < 0);
    }

    @Test
    public void testBinaryCopyOfArray() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String[] copy = Arrays.copyOf(arr, 10);

        Assert.assertEquals(10, copy.length);
        Assert.assertEquals("b", copy[1]);
        Assert.assertNull(copy[5]);

        copy = Arrays.copyOf(arr, 3);

        Assert.assertEquals(3, copy.length);
        Assert.assertEquals("c", copy[2]);
    }

    @Test
    public void testBinaryCopyOfRange() {
        String[] arr = {"a", "b", "c", "d", "e"};

        String[] copy = Arrays.copyOfRange(arr, 1, 4);

        Assert.assertEquals(3, copy.length);
        Assert.assertEquals("b", copy[0]);
        Assert.assertEquals("c", copy[1]);
        Assert.assertEquals("d", copy[2]);
    }

    @Test
    public void testJDK9Compare() {
        // the whole arrays
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "b"};
        Assert.assertEquals(0, Arrays.compare(arr1, arr2));

        arr1 = new String[]{"a"};
        Assert.assertTrue(Arrays.compare(arr1, arr2) < 0);

        arr1 = new String[]{"a", "v"};
        Assert.assertTrue(Arrays.compare(arr1, arr2) > 0);

        // subarray
        arr1 = new String[] {"1", "a", "b", "2"};
        arr2 = new String[] {"1", "1", "a", "b", "2", "2"};
        Assert.assertEquals(0, Arrays.compare(arr1, 1, 3, arr2, 2, 4));
    }

    @Test
    public void testJDK9Mismatch() {
        // the whole arrays
        String[] arr1 = {"a", "b"};
        String[] arr2 = {"a", "b"};
        Assert.assertEquals(-1, Arrays.mismatch(arr1, arr2));

        arr1 = new String[]{"a", "b", "c"};
        Assert.assertEquals(2, Arrays.mismatch(arr1, arr2));

        // subarray
        arr1 = new String[] {"1", "a", "b", "2"};
        arr2 = new String[] {"1", "1", "a", "b", "2", "2"};
        Assert.assertEquals(2, Arrays.mismatch(arr1, 1, 3, arr2, 2, 5));
    }

    @Test
    public void testEquals() {
        String[] arr = {"a", "b", "c", "d", "e"};
        String[] arr2 = {"a", "b"};
        String[] arr3 = {"b", "a", "c", "d", "e"};
        String[] arr4 = {"a", "b", "c", "d", "e"};

        Assert.assertFalse(Arrays.equals(arr, arr2));
        Assert.assertFalse(Arrays.equals(arr, arr3));
        Assert.assertTrue(Arrays.equals(arr, arr4));

        // JDK9
        Assert.assertTrue(Arrays.equals(arr, 2, 4, arr4, 2, 4));
    }

    @Test
    public void testDeepEqualsStringArrays() {
        String[] arr = {"a", "b", "c", "d", "e"};
        String[] arr2 = {"a", "b"};
        String[] arr3 = {"b", "a", "c", "d", "e"};
        String[] arr4 = {"a", "b", "c", "d", "e"};

        Assert.assertFalse(Arrays.deepEquals(arr, arr2));

        // TODO: test arrays
    }

    @Test
    public void testJDK9Equals() {

    }

    @Test
    public void testFill() {
        String[] arr = new String[10];

        Arrays.fill(arr, "foo");

        Assert.assertEquals("foo", arr[0]);
        Assert.assertEquals("foo", arr[9]);
    }

    @Test
    public void testFillRange() {
        String[] arr = new String[10];

        Arrays.fill(arr, 3, 6, "foo");

        Assert.assertNull(arr[2]);
        Assert.assertEquals("foo", arr[3]);
        Assert.assertEquals("foo", arr[5]);
        Assert.assertNull(arr[6]);
    }

    @Test
    public void testSort() {
        String[] arr = {"b", "a", "e", "c", "d"};

        Arrays.sort(arr);

        Assert.assertEquals("a", arr[0]);
        Assert.assertEquals("e", arr[4]);
    }

    @Test
    public void testSortWithComparator() {
        String[] arr = {"bbb", "aa", "e", "cccc", "ddddd"};

        Arrays.sort(arr, Comparator.comparingInt(String::length));

        Assert.assertEquals("e", arr[0]);
        Assert.assertEquals("aa", arr[1]);
        Assert.assertEquals("bbb", arr[2]);
        Assert.assertEquals("cccc", arr[3]);
        Assert.assertEquals("ddddd", arr[4]);
    }

    @Test
    public void testParallelSort() {
        String[] arr = {"b", "a", "e", "c", "d"};

        Arrays.parallelSort(arr);

        Assert.assertEquals("a", arr[0]);
        Assert.assertEquals("e", arr[4]);
    }

    @Test
    public void testParallelSortWithComparator() {
        String[] arr = {"bbb", "aa", "e", "cccc", "ddddd"};

        Arrays.parallelSort(arr, Comparator.comparingInt(String::length));

        Assert.assertEquals("e", arr[0]);
        Assert.assertEquals("aa", arr[1]);
        Assert.assertEquals("bbb", arr[2]);
        Assert.assertEquals("cccc", arr[3]);
        Assert.assertEquals("ddddd", arr[4]);
    }

    // TODO: write test - have a look at spliterator
    @Test
    public void testSpliterator() {
        int[] arr = {1, 3, 2, 4};

        Spliterator.OfInt spliterator = Arrays.spliterator(arr);
    }

    @Test
    public void testStream() {
        int[] arr = {1, 2, 3, 4};

        IntStream stream = Arrays.stream(arr);
        stream.forEach(i -> Assert.assertTrue(i >= 1 && i <= 4));

    }

    @Test
    public void testStreamWithRange() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};

        IntStream stream = Arrays.stream(arr, 2, 6);

        stream.forEach(i -> Assert.assertTrue(i >= 3 && i <= 6));
    }

    @Test
    public void testSetAll() {
        int[] arr = {1, 2, 3};

        Arrays.setAll(arr, i -> arr[i] * 10);

        Assert.assertEquals(10, arr[0]);
        Assert.assertEquals(20, arr[1]);
        Assert.assertEquals(30, arr[2]);
    }

    @Test
    public void testParallelSetAll() {
        String[] arr = {"1", "22", "333"};

        Arrays.parallelSetAll(arr, i -> String.valueOf(arr[i].length() * 10));

        Assert.assertEquals("10", arr[0]);
        Assert.assertEquals("20", arr[1]);
        Assert.assertEquals("30", arr[2]);
    }

    @Test
    public void testParallelPrefix() {
        int[] arr = {1, 2, 3, 5};

        Arrays.parallelPrefix(arr, (a, b) -> a * b);

        Assert.assertEquals(1, arr[0]);
        Assert.assertEquals(2, arr[1]);
        Assert.assertEquals(6, arr[2]);
        Assert.assertEquals(30, arr[3]);
    }

    @Test
    public void testParallelPrefixWithStrings() {
        String[] arr = {"1", "2", "3", "4"};

        Arrays.parallelPrefix(arr, (a, b) -> a + b);

        Assert.assertEquals("1", arr[0]);
        Assert.assertEquals("12", arr[1]);
        Assert.assertEquals("123", arr[2]);
        Assert.assertEquals("1234", arr[3]);
    }

    @Test
    public void testToString() {
        String[] arr = {"1", "2", "3", "4"};
        //TODO: Have a look at the implementation
        Assert.assertEquals("[1, 2, 3, 4]", Arrays.toString(arr));
    }

    @Test
    public void testHashCode() {
        String[] arr = {"1", "2", "3", "4"};
        //TODO: Have a look at the implementation
        Arrays.hashCode(arr);
    }

    @Test
    public void testDeepToString() {
        String[][] arr = {{"1", "2"}, {"3", "4"}};
        //TODO: Have a look at the implementation
        Assert.assertEquals("[[1, 2], [3, 4]]", Arrays.deepToString(arr));
    }
}
