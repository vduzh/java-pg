package by.duzh.jse.oop;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Outer {
    int outerField = 100;
    private int privateOuterField = 110;
    static int outerStaticField = 120;

    int outerMethod() {
        return 200;
    }

    // it behaves like another member of the Outer
    class Inner {
        int innerField = 300;

        int innerMethod() {
            return 400;
        }

        int accessOuterMember() {
            // it has access like any other member of the Outer
            return outerField * privateOuterField;
        }
    }

    int useInner() {
        Inner inner = new Inner();
        return inner.innerMethod();
    }

    static class InnerStatic {
        int innerMethod() {
            return 500;
        }

        int accessOuterStaticMember() {
            // it has access like any other member of the Outer
            return outerStaticField * Outer.outerStaticField;
        }
    }
}

public class InnerClassesTest {

    @Test
    public void testCreateInner() {
        // traditional approach
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();

        // simpler version
        inner = new Outer().new Inner();
    }

    @Test
    public void testCreateInnerStatic() {
        Outer.InnerStatic inner = new Outer.InnerStatic();
    }

    @Test
    public void testInnerForLocalBlock() {
        class InnerLocal {
        }
        InnerLocal il = new InnerLocal();

        {
            class InnerLocal2 {
            }
            InnerLocal2 il2 = new InnerLocal2();
        }
    }

    @Test
    public void testUseInnerInOuter() {
        Outer outer = new Outer();
        Assertions.assertEquals(400, outer.useInner());
    }

    @Test
    public void testImpossibleUseInnerMembersFromOuter() {
        Outer outer = new Outer();
        // Impossible!!!
        //outer.innerMethod()
    }

    @Test
    public void testAccessToOuterMembers() {
        Outer.Inner inner = new Outer().new Inner();
        Assertions.assertEquals(11_000, inner.accessOuterMember());

        Outer.InnerStatic innerStatic = new Outer.InnerStatic();
        Assertions.assertEquals(144_00, innerStatic.accessOuterStaticMember());
    }
}
