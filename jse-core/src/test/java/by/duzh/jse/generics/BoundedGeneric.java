package by.duzh.jse.generics;

/**
 * Sample of the bounded generic.
 *
 * @param <T> ant type that extends the Number class.
 */
public class BoundedGeneric<T extends Number> {
    private final T value;

    public BoundedGeneric(T value) {
        this.value = value;
    }

    public double getDouble() {
        return value.doubleValue();
    }
}

interface SomeInterface {
    default String foo() {
        return "foo";
    }
}

/**
 * Sample of the bounded to an interface generic.
 *
 * @param <T> ant type that extends the SomeInterface.
 */
class BoundedToInterfaceGeneric<T extends SomeInterface> {
    private final T value;

    public BoundedToInterfaceGeneric(T value) {
        this.value = value;
    }

    public String say() {
        return value.foo();
    }
}

interface SecondInterface {
    default String bar() {
        return "bar";
    }
}

/**
 * Sample of the bounded to interfaces generic.
 *
 * @param <T> ant type that extends both the SomeInterface and the SecondInterface.
 */
class BoundedToMultipleInterfacesGeneric<T extends SomeInterface & SecondInterface> {
    private final T value;

    public BoundedToMultipleInterfacesGeneric(T value) {
        this.value = value;
    }

    public String say() {
        return value.foo() + " and " + value.bar();
    }
}

class Container {
    public int getNumber() {
        return 100;
    }
}

/**
 * Sample of the bounded to both a class and an interface generic.
 *
 * @param <T> ant type that extends the Number, SomeInterface and SecondInterface.
 */
class BoundedToClassAndInterfacesGeneric<T extends Container & SomeInterface & SecondInterface> {
    private final T value;

    public BoundedToClassAndInterfacesGeneric(T value) {
        this.value = value;
    }

    public String say() {
        return value.getNumber() + " and " + value.foo() + " and " + value.bar();
    }
}

