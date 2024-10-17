package by.duzh.jse.lambda.foo;

public class MyString {
    private String s;

    public MyString() {
        this.s = "";
    }

    public MyString(String s) {
        this.s = s;
    }

    public String getValue() {
        return s;
    }

    public boolean compareToInteger(Integer i) {
        return Integer.valueOf(s).equals(i);
    }
}
