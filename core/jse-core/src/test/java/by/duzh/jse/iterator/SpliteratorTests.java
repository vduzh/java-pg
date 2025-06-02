package by.duzh.jse.iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Spliterator;

public class SpliteratorTests {
    private static ArrayList<Integer> myList = new ArrayList<>();

    @BeforeAll
    public static void initMyList() {
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);
        myList.add(6);
    }

    @Test
    public void testIterate() {
        Spliterator<Integer> spliterator = myList.spliterator();
        while (spliterator.tryAdvance(System.out::println)) ;
    }

    @Test
    public void tesForEachRemaining() {
        Spliterator<Integer> spliterator = myList.spliterator();
        spliterator.forEachRemaining(System.out::println);
    }

    @Test
    public void tesTrySplit() {
        Spliterator<Integer> spliterator = myList.spliterator();

        Spliterator<Integer> spliterator1 = spliterator.trySplit();
        if (spliterator1 != null) {
            System.out.println("spliterator1:");
            spliterator1.forEachRemaining(System.out::println);
        }

        Spliterator<Integer> spliterator2 = spliterator.trySplit();
        if (spliterator2 != null) {
            System.out.println("spliterator2:");
            spliterator2.forEachRemaining(System.out::println);
        }

        System.out.println("spliterator:");
        spliterator.forEachRemaining(System.out::println);
    }
}
