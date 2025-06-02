package by.duzh.jse.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@interface AnnotationForClassTest {
    int i();
}

class SomeSuperClass {
    private char c;

    public SomeSuperClass(char c) {
        this.c = c;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }
}

@AnnotationForClassTest(i = 1)
class SomeClass extends SomeSuperClass implements Serializable {
    private int i;
    private String s;

    public SomeClass() {
        this("");
    }

    public SomeClass(String s) {
        super('a');
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public static class SubClass {
    }

    public interface SubInterface {
    }
}

@Disabled
public class ClassTest {
    private final String NAME = "by.duzh.jse.lang.SomeClass";

    @Test
    public void testClassForName() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Assertions.assertEquals(klass.getName(), NAME);
    }

    @Test
    public void testGetAnnotations() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Annotation[] annotations = klass.getAnnotations();
        Assertions.assertEquals(1, annotations.length);
    }

    @Test
    public void testGetAnnotation() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Annotation annotation = klass.getAnnotation(AnnotationForClassTest.class);
        Assertions.assertNotNull(annotation);
    }

    @Test
    public void testGetAnnotationsByType() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Annotation[] annotations = klass.getAnnotationsByType(AnnotationForClassTest.class);
        Assertions.assertEquals(1, annotations.length);
    }

    @Test
    public void testGetClasses() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Class<?>[] classes = klass.getClasses();
        Assertions.assertEquals(2, classes.length);
    }

    @Test
    public void testGetConstructors() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?>[] constructors = klass.getConstructors();
        Assertions.assertEquals(2, constructors.length);
    }

    @Test
    public void testGetDefaultConstructor() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?> constructor = klass.getConstructor();
        Assertions.assertNotNull(constructor);
    }

    @Test
    public void testGetDefaultConstructorWithParams() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Constructor<?> constructor = klass.getConstructor(String.class);
        Assertions.assertNotNull(constructor);
    }

    @Test
    public void testGetDeclaredFields() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Field[] fields = klass.getDeclaredFields();
        Assertions.assertEquals(2, fields.length);
    }

    @Test
    public void testGetFields() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Field[] fields = klass.getFields();
        Assertions.assertEquals(3, fields.length);
    }

    @Test
    public void testGetDeclaredMethods() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Method[] methods = klass.getDeclaredMethods();
        Assertions.assertEquals(2, methods.length);
    }

    @Test
    public void testGetField() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Field field = klass.getDeclaredField("s");
    }

    @Test
    public void testGetFieldError() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Assertions.assertThrows(NoSuchFieldException.class, () -> {
            Field field = klass.getDeclaredField("s111");
        });
    }

    @Test
    public void testGetMethod() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Method method = klass.getDeclaredMethod("getI");
    }

    @Test
    public void testGetMethodError() throws Exception {
        Class<?> klass = Class.forName(NAME);
        Assertions.assertThrows(NoSuchMethodException.class, () -> {
            Method method = klass.getDeclaredMethod("getI2");
        });
    }
}
