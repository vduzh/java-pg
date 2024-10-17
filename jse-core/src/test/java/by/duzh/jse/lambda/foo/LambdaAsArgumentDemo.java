package by.duzh.jse.lambda.foo;

public class LambdaAsArgumentDemo {
    public static String stringOperation(StringFunc sf, String s) {
        return sf.func(s);
    }

    public static String stringOperationWithoutValue(StringFunc sf) {
        return sf.func("Foo");
    }

    public static boolean compareStringsToInt(CompareFunc cf, String[] strings, Integer intValue) {
        boolean result = false;

        for (String s: strings)
            result = result || cf.equal(new MyString(s), intValue);

        return result;
    }

}
