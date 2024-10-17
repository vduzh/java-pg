package by.duzh.jse.util.stream;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class StreamCoreTests {
    private static ArrayList<Integer> myList = new ArrayList<>();

    @BeforeClass
    public static void initMyList() {
        myList.add(7);
        myList.add(18);
        myList.add(10);
        myList.add(24);
        myList.add(17);
        myList.add(10);
        myList.add(5);
    }

    @Test
    public void testForEach(){
        Stream<Integer> stream = myList.stream();
        stream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testMin(){
        Stream<Integer> stream = myList.stream();
        Optional<Integer> minValue = stream.min(Integer::compare);
        if (minValue.isPresent()) {
            System.out.println("Min value: " + minValue.get());
        }
    }

    @Test
    public void testMax(){
        Stream<Integer> stream = myList.stream();
        Optional<Integer> maxValue = stream.max(Integer::compare);
        if (maxValue.isPresent()) {
            System.out.println("Max value: " + maxValue.get());
        }
    }

    @Test
    public void testSortDefault(){
        Stream<Integer> stream = myList.stream();
        Stream<Integer> sortedStream = stream.sorted();

        sortedStream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testSortWithComparator(){
        Stream<Integer> stream = myList.stream();
        Stream<Integer> sortedStream = stream.sorted((i, j) -> i + 2 - j);

        sortedStream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testFilterNegative(){
        Stream<Integer> stream = myList.stream();
        Stream<Integer> filteredStream = stream.filter((n) -> (n % 2) == 1);
        filteredStream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testFilterPositive(){
        Stream<Integer> stream = myList.stream();
        Stream<Integer> filteredStream = stream.filter((n) -> (n % 2) == 0);
        filteredStream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testFilterCustom(){
        Stream<Integer> stream = myList.stream();
        Stream<Integer> filteredStream = stream
                .filter((n) -> (n % 2) == 1)
                .filter(n -> n < 10);
        filteredStream.forEach((n) -> System.out.print(n + " "));
    }

    @Test
    public void testAllMatch(){
        Stream<Integer> stream = myList.stream();
        boolean match = stream.allMatch((i) -> i < 100);
        System.out.println(match? "All Match": "All Not Match");
    }

    @Test
    public void testAnyMatch(){
        Stream<Integer> stream = myList.stream();
        boolean match = stream.anyMatch((i) -> i < 10);
        System.out.println(match? "Any Match": "All Not Match");
    }

    @Test
    public void testNoneMatch(){
        Stream<Integer> stream = myList.stream();
        boolean match = stream.noneMatch((i) -> i < 10);
        System.out.println(match? "None Match": "Any Match");
    }

    @Test
    public void testCount(){
        Stream<Integer> stream = myList.stream();
        System.out.println("Count :" + stream.count());
    }

    @Test
    public void testDistinct(){
        myList.stream().distinct().forEach(System.out::println);
    }

    @Test
    public void testOf(){
        Stream<Integer> stream = Stream.of(1, 2, 3);

        stream.forEach(System.out::println);
    }
}
