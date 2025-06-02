package by.duzh.jse.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class ObservableTest {
    public static class MyObservable extends Observable {
        public void doSomething(String s) {
            setChanged();
        }
    }

    public static class MyObserver implements Observer {
        private String value;

        public String getValue() {
            return value;
        }

        @Override
        public void update(Observable o, Object arg) {
            this.value = (String) arg;
        }
    }

    private MyObservable observable;

    @BeforeEach
    public void init() {
        observable = new MyObservable();
    }

    @Test
    public void testAddObserver() {
        observable.addObserver((observable, obj) -> System.out.println("foo"));
    }

    @Test
    public void testCountObservers() {
        Assertions.assertEquals(0, observable.countObservers());

        observable.addObserver((observable, obj) -> System.out.println("foo"));
        observable.addObserver((observable, obj) -> System.out.println("bar"));

        Assertions.assertEquals(2, observable.countObservers());
    }

    @Test
    public void testDeleteObserver() {
        Observer observer = (observable, obj) -> System.out.println("foo");
        observable.addObserver(observer);

        Assertions.assertEquals(1, observable.countObservers());
        observable.deleteObserver(observer);
        Assertions.assertEquals(0, observable.countObservers());
    }

    @Test
    public void testDeleteObservers() {
        Assertions.assertEquals(0, observable.countObservers());

        observable.addObserver((observable, obj) -> System.out.println("foo"));
        observable.addObserver((observable, obj) -> System.out.println("bar"));

        Assertions.assertEquals(2, observable.countObservers());

        observable.deleteObservers();
        Assertions.assertEquals(0, observable.countObservers());
    }

    @Test
    public void testSetChanged() {
        observable.doSomething(""); // Called inside this method
    }

    @Test
    public void testHasChanged() {
        Assertions.assertFalse(observable.hasChanged());

        observable.doSomething("");

        Assertions.assertTrue(observable.hasChanged());
    }

    @Test
    public void test() {
        MyObserver observer = new MyObserver();

        observable.addObserver(observer);
        observable.doSomething("");
        observable.notifyObservers("foo");

        Assertions.assertEquals("foo", observer.getValue());
    }


}
