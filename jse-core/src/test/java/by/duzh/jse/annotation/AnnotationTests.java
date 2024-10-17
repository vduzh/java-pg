package by.duzh.jse.annotation;

import org.junit.Assert;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;

// A very simple annotation with 2
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@interface SomeAnnotation {
    String str();
    int val();
}

@Retention(RetentionPolicy.RUNTIME)
@interface AnnotationWithDefaultValues {
    String someValue() default "none";
}

@Retention(RetentionPolicy.RUNTIME)
@interface Marker {
}

@Retention(RetentionPolicy.RUNTIME)
@interface SingleMemberAnnotation {
    int value();
}

@Retention(RetentionPolicy.RUNTIME)
@interface SecondAnnotation {
    String str();
}

// Repeatable annotation
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SomeRepeatableContainer.class)
@interface SomeRepeatable {
    String str();
}

@Retention(RetentionPolicy.RUNTIME)
@interface SomeRepeatableContainer {
    SomeRepeatable[] value();
}

class Foo {
    @SomeAnnotation(str="annotation sample", val=100)
    public void bar() {
        System.out.println("bar is working...");
    }

    @AnnotationWithDefaultValues()
    public void buzz() {
    }

    @Marker
    @SingleMemberAnnotation(2)
    public void hello() {
    }

    public void thisMethod(Foo this, String s) {
        System.out.println(this);
        System.out.println(s);
    }

    @SomeRepeatable(str="foo")
    @SomeRepeatable(str="bar")
    public void repeatable() {
    }

    @SomeAnnotation(str="annotation sample", val=100)
    public void overridden() {
    }
}

class FooSubClass extends Foo {

    @SecondAnnotation(str="buzz")
    public void buzz(String s) {
        System.out.println("buzz is working");
    }

    @Override
    @SecondAnnotation(str="overridden")
    public void overridden() {
        super.overridden();
    }
}

public class AnnotationTests {
    @Test
    public void testMethodGetAnnotationOK() throws Exception {
        Class klass = Foo.class;
        Method method = klass.getMethod("bar");
        // Get a method annotation
        SomeAnnotation annotation = method.getAnnotation(SomeAnnotation.class);
        Assert.assertEquals(annotation.str(), "annotation sample");
        Assert.assertEquals(annotation.val(), 100);
    }

    @Test
    public void testMethodGetAnnotationsOk() throws Exception {
        Annotation[] annotations = Foo.class.getMethod("bar").getAnnotations();
        Assert.assertEquals(annotations.length, 1);
        Assert.assertTrue(annotations[0] instanceof SomeAnnotation);
    }

    @Test
    public void testMethodGetDeclaredAnnotationsOk() throws Exception {
        Annotation[] annotations = FooSubClass.class.getMethod("buzz", String.class).getDeclaredAnnotations();
        Assert.assertEquals(annotations.length , 1);
        Assert.assertTrue(annotations[0] instanceof SecondAnnotation);
    }

    @Test
    public void testMethodIsAnnotationPresent() throws Exception {
        Class<FooSubClass> klass = FooSubClass.class;

        Method buzz = klass.getMethod("buzz", String.class);
        Method bar = klass.getMethod("bar");

        Assert.assertTrue(buzz.isAnnotationPresent(SecondAnnotation.class));
        Assert.assertTrue(bar.isAnnotationPresent(SomeAnnotation.class));
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        Class<FooSubClass> klass = FooSubClass.class;

        Method method = klass.getMethod("overridden");
        Assert.assertTrue(method.isAnnotationPresent(SecondAnnotation.class));
        Assert.assertFalse(method.isAnnotationPresent(SomeAnnotation.class));
    }

    @Test
    public void testMethodAnnotationWithDefaultValuesOK() throws Exception {
        Assert.assertEquals("none",
                Foo.class.getMethod("buzz").getAnnotation(AnnotationWithDefaultValues.class).someValue());
    }

    @Test
    public void testMethodHasMarkerOK() throws Exception {
        Assert.assertTrue(Foo.class.getMethod("hello").isAnnotationPresent(Marker.class));
    }

    @Test
    public void testMethodHasNoMarkerOK() throws Exception {
        Assert.assertFalse(Foo.class.getMethod("buzz").isAnnotationPresent(Marker.class));
    }

    @Test
    public void testSingleValueAnnotationOk() throws Exception {
        Assert.assertEquals(Foo.class.getMethod("hello").getAnnotation(SingleMemberAnnotation.class).value(), 2);
    }

    @Test
    public void testThisMethodOk() throws Exception {
        Foo foo = new Foo();
        foo.thisMethod("test");
    }

    @Test
    public void testRepeatableAnnotationOK() throws Exception {
        Method method = Foo.class.getMethod("repeatable");
        SomeRepeatableContainer annotation = method.getAnnotation(SomeRepeatableContainer.class);
        for (SomeRepeatable repeatable: annotation.value()) {
            Assert.assertTrue("foo".equals(repeatable.str()) || "bar".equals(repeatable.str()));
        }
    }

    @Test
    public void testRepeatableAnnotationWithGetAnnotationsByTypeOK() throws Exception {
        Method method = Foo.class.getMethod("repeatable");
        for (SomeRepeatable repeatable: method.getAnnotationsByType(SomeRepeatable.class)) {
            Assert.assertTrue("foo".equals(repeatable.str()) || "bar".equals(repeatable.str()));
        }
    }

    // TODO: test Type annotations
}

