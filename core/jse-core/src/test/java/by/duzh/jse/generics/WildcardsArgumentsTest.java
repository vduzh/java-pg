package by.duzh.jse.generics;

import org.junit.Test;

import java.lang.reflect.Array;

class WAClass {
}

class WBClass extends WAClass {
}

class WCClass extends WBClass {
}

class WContainer<T extends WAClass> {
    private T[] objects;

    public WContainer(T[] objects) {
        this.objects = objects;
    }

    public T[] getObjects() {
        return objects;
    }

//    @SuppressWarnings("unchecked")
    public void addA(WContainer<?> objects) {
//        T[] res = (T[]) Array.newInstance(
//                this.objects.getClass().getComponentType(),
//                this.objects.length + objects.getObjects().length);
//        System.arraycopy(res, 0, this.objects, 0, this.objects.length);
//        System.arraycopy(res, this.objects.length, objects.getObjects(), 0, objects.getObjects().length);
//
//        this.objects = res;
    }

    public void addB(WContainer<? extends WBClass> objects) {

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

