package by.duzh.jse.generics;

public class SimpleGeneric<T> {
    private T value;

    public SimpleGeneric(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public String getValueClassName() {
        return value.getClass().getName();
    }
}
