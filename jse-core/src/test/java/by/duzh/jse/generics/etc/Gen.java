package by.duzh.jse.generics.etc;

public class Gen<T> {
    private T value;

    public Gen(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public String getValueClassName() {
        return this.value.getClass().getName();
    }
}
