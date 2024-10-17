package by.duzh.jse.lambda.foo;

public class MyArrayOps {
    public static <T> int countMatching(T[] values, T value) {
        int res = 0;

        for (T obj: values) {
            if (obj == value) {
                res++;
            }
        }

        return res;
    }
}
