package by.duzh.jse.generics;

import org.junit.Test;
import org.w3c.dom.CDATASection;

class WAClass {}

class WBClass extends WAClass {}

class WCClass extends WBClass {}

class WContainer<T extends WAClass> {
    private final T[] objects;

    public WContainer(T[] objects) {
        this.objects = objects;
    }

    public T[] getObjects() {
        return objects;
    }

    public void addA(WContainer<?> objects) {
        //this.objects.
    }

    public void addB(WContainer <? extends WBClass> objects) {

    }

    public void addC(WContainer<? super WCClass> objects) {

    }

}

public class WildcardsArgumentsTest {

    @Test
    public void testWildcardArgumentsWithA() {
        WAClass[] data1 = {new WAClass(), new WAClass()};
        WContainer<WAClass> container1 = new WContainer<WAClass>(data1);

        WAClass[] data2 = {new WAClass(), new WAClass()};
        WContainer<WAClass> container2 = new WContainer<WAClass>(data2);

        container1.addA(container2);
    }

    @Test
    public void testWildcardArgumentsWithB() {
        WAClass[] data1 = {new WAClass(), new WAClass()};
        WContainer<WAClass> container1 = new WContainer<WAClass>(data1);

        WBClass[] data2 = {new WBClass(), new WBClass()};
        WContainer<WBClass> container2 = new WContainer<WBClass>(data2);

        container1.addB(container2);
    }

    @Test
    public void testWildcardArgumentsWithV() {
        WAClass[] data1 = {new WAClass(), new WAClass()};
        WContainer<WAClass> container1 = new WContainer<WAClass>(data1);

        WCClass[] data2 = {new WCClass(), new WCClass()};
        WContainer<WCClass> container2 = new WContainer<WCClass>(data2);

        container1.addC(container2);
    }
}

