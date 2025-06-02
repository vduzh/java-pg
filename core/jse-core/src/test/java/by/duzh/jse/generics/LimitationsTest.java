package by.duzh.jse.generics;

import org.junit.jupiter.api.Test;

public class LimitationsTest {
    @Test
    public void impossibleCreateInstanceOfType() {
//        class Foo<T> {
//            T value;
//
//            Foo() {
//                value = new T();
//            }
//        }
    }

    @Test
    public void staticMembersCanNotUseTypeOfOuterClass() {
//        class Foo<T> {
//            static T value;
//
//            static T getValue() {
//                return value;
//            }
//        }
    }

    @Test
    public void canNotCreateArrayWithTypeElements() {
//        class Foo<T extends Number> {
//            T value;
//            T[] values;
//
//            public Foo() {
//                // impossible as compiler does not know the type of the elements
//                values = new T[];
//            }
//        }
    }
}
