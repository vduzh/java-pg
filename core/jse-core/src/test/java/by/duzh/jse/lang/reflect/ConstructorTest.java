package by.duzh.jse.lang.reflect;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;

@Retention(RetentionPolicy.RUNTIME)
@interface AnnotationForClassTest {
    int i();
}

@AnnotationForClassTest(i=1)
class SomeClass {
    private String s;

    public SomeClass() throws RuntimeException {
        this("");
    }

    public SomeClass(String s) throws RuntimeException {
        this.s = s;
    }

    public static class SubClass {}

    public interface  SubInterface {}
}

public class ConstructorTest {
    private final String NAME = "by.duzh.jse.lang.reflect.SomeClass";

    @Test
    public void testGettConstructors() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?>[] constructors = klass.getConstructors();
        Assertions.assertEquals(2, constructors.length);
    }

    @Test
    public void testGetDefaultConstructor() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?> constructor = klass.getConstructor(new Class[0]);
        Assertions.assertNotNull(constructor);
    }

    @Test
    public void testGetDefaultConstructorWithParams() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?> constructor = klass.getConstructor(String.class);
        Assertions.assertNotNull(constructor);
        Assertions.assertEquals(NAME, constructor.getDeclaringClass().getName());
        Assertions.assertEquals(1, constructor.getExceptionTypes().length);
        //Assert.assertNotNull(constructor.);
    }
}
