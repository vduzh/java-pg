package by.duzh.jse.util.stream;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Stream;

public class ReduceTests {
    private static ArrayList<Integer> myList = new ArrayList<>();

    @BeforeClass
    public static void initMyList() {
        myList.add(7);
        myList.add(18);
        myList.add(10);
        myList.add(24);
        myList.add(17);
        myList.add(5);
    }

    @Test
    public void testMultipleAll() {
        Stream<Integer> stream = myList.stream();
        Optional<Integer> result = stream.reduce((currentResOrFirstItem, item) -> currentResOrFirstItem * item);
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }

    @Test
    public void testMultipleAll2() {
        Stream<Integer> stream = myList.stream();
        Integer result = stream.reduce(1, (currentRes, item) -> currentRes * item);
        System.out.println(result);
    }

    @Test
    public void testMultipleEven() {
        Stream<Integer> stream = myList.stream();
        Optional<Integer> result = stream.reduce((currentResOrFirstItem, item) -> {
            if (item % 2 == 0)
                return currentResOrFirstItem * item;
            else
                return currentResOrFirstItem;
        });
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }

    @Test
    public void testMultipleEven2() {
        Stream<Integer> stream = myList.stream();
        Integer result = stream.reduce(1, (currentRes, item) -> {
            if (item % 2 == 0)
                return currentRes * item;
            else
                return currentRes;
        });
        System.out.println(result);
    }

    @Test
    public void testSumEven2() {
        Stream<Integer> stream = myList.stream();
        Integer result = stream.reduce(0, (currentRes, item) -> {
            if (item % 2 == 0)
                return currentRes + item;
            else
                return currentRes;
        });
        System.out.println(result);
    }
}
