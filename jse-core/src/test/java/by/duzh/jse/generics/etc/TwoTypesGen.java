package by.duzh.jse.generics.etc;

public class TwoTypesGen<T extends Number, V> extends NumberGen<T> {
    private final V data;

    public TwoTypesGen(T value, V data) {
        super(value);
        this.data = data;
    }

    public V getData() {
        return data;
    }
}
