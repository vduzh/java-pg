package by.duzh.jse.lambda.foo;

public interface MyObjectCreator<T, R> {
    R create(T value);
}
