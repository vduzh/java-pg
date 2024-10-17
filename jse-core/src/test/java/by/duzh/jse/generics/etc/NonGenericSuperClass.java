package by.duzh.jse.generics.etc;

public class NonGenericSuperClass<T> extends NonGen {

    private final T data;

    public NonGenericSuperClass(T data, int value) {
        super(value);
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
