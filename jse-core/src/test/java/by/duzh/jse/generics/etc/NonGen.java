package by.duzh.jse.generics.etc;

public class NonGen {
    private int value;

    public NonGen() {
        value = 0;
    }

    public NonGen(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String bar() {
        return "bar";
    }
}
