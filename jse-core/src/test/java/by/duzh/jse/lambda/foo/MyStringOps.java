package by.duzh.jse.lambda.foo;

public class MyStringOps {
    public static String reverse(String s) {
        String result = "";

        for (int i = s.length() - 1; i >= 0; i--) {
            result += s.charAt(i);
        }

        return result;
    }

    public String upperCase(String s) {
        return s.toUpperCase();
    }
}

