package by.duzh.jse.annotation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.lang.annotation.*;
import java.lang.reflect.Method;

import static java.lang.annotation.ElementType.*;

/**
 * A very simple annotation with several members.
 */
@Target({TYPE, FIELD, CONSTRUCTOR, METHOD, PARAMETER, LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@interface CustomAnnotation {
    // annotation member str
    String str();

    // annotation member val
    int val();

    // the member with default value
    String someValue() default "none";
}

/**
 * An annotation with only member.
 */
@Retention(RetentionPolicy.RUNTIME)
@interface SingleMemberAnnotation {
    int value();
}

/**
 * A marker annotation that has no members
 */
@Retention(RetentionPolicy.RUNTIME)
@interface Marker {
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
    @CustomAnnotation(str = "annotation sample", val = 100)
    @SingleMemberAnnotation(200)
    @Marker
    @SomeRepeatable(str = "one")
    @SomeRepeatable(str = "two")
    public void bar() {
        System.out.println("Foo::bar is working...");
    }

//    public void thisMethod(Foo this, String s) {
//        System.out.println(this);
//        System.out.println(s);
//    }


    @CustomAnnotation(str = "annotation sample", val = 100)
    public void overridden() {
    }
}

/**
 * Override annotations in the subclass
 */
class FooSubClass extends Foo {
    @SecondAnnotation(str = "overridden")
    @Override
    public void bar() {
        System.out.println("FooSubClass::bar is working...");
    }

    @SecondAnnotation(str = "overridden")
    @Override
    public void overridden() {
        super.overridden();
    }
}

public class AnnotationTests {
    @Test
    public void getAnnotation() throws Exception {
        // get the of the class
        Class<?> klass = Foo.class;
        Method method = klass.getMethod("bar");

        // Get a method annotation
        CustomAnnotation annotation = method.getAnnotation(CustomAnnotation.class);
        Assertions.assertEquals(annotation.str(), "annotation sample");
        Assertions.assertEquals(annotation.val(), 100);
    }

    @Test
    public void getAnnotations() throws Exception {
        Annotation[] annotations = Foo.class.getMethod("bar").getAnnotations();
        Assertions.assertEquals(annotations.length, 4);
    }

    @Test
    public void geDeclaredAnnotations() throws Exception {
        Annotation[] annotations = FooSubClass.class.getMethod("bar").getDeclaredAnnotations();
        Assertions.assertEquals(1, annotations.length);
        Assertions.assertTrue(annotations[0] instanceof SecondAnnotation);
    }

    @Test
    public void isAnnotationPresent() throws Exception {
        Class<Foo> klass = Foo.class;
        Method method = klass.getMethod("bar");
        Assertions.assertTrue(method.isAnnotationPresent(CustomAnnotation.class));
        Assertions.assertTrue(method.isAnnotationPresent(SingleMemberAnnotation.class));
        Assertions.assertTrue(method.isAnnotationPresent(Marker.class));
        Assertions.assertTrue(method.isAnnotationPresent(SomeRepeatableContainer.class));
        Assertions.assertFalse(method.isAnnotationPresent(Retention.class));
    }

    @Test
    public void overrideMethods() throws Exception {
        Class<FooSubClass> klass = FooSubClass.class;

        Method method = klass.getMethod("overridden");
        Assertions.assertTrue(method.isAnnotationPresent(SecondAnnotation.class));
        Assertions.assertFalse(method.isAnnotationPresent(CustomAnnotation.class));
    }


    @Test
    public void repeatableAnnotation() throws Exception {
        Method method = Foo.class.getMethod("bar");

        SomeRepeatableContainer annotationContainer = method.getAnnotation(SomeRepeatableContainer.class);
        for (SomeRepeatable repeatable : annotationContainer.value()) {
            Assertions.assertTrue("one".equals(repeatable.str()) || "two".equals(repeatable.str()));
        }
    }

    @Test
    public void testRepeatableAnnotationWithGetAnnotationsByTypeOK() throws Exception {
        Method method = Foo.class.getMethod("bar");
        for (SomeRepeatable repeatable : method.getAnnotationsByType(SomeRepeatable.class)) {
            Assertions.assertTrue("one".equals(repeatable.str()) || "two".equals(repeatable.str()));
        }
    }

//    @Test
//    public void testThisMethodOk() throws Exception {
//        Foo foo = new Foo();
//        foo.thisMethod("test");
//    }


    //TODO: test Type annotations
}

