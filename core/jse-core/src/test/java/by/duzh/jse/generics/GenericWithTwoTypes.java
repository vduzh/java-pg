package by.duzh.jse.generics;

public class GenericWithTwoTypes<T, V> {
    private final T obj1;
    private final V obj2;

    public GenericWithTwoTypes(T obj1, V obj2) {
        this.obj1 = obj1;
        this.obj2 = obj2;
    }

    public T getObj1() {
        return obj1;
    }

    public V getObj2() {
        return obj2;
    }
}
