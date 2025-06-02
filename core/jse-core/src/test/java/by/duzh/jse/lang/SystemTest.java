package by.duzh.jse.lang;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class SystemTest {

    @Test
    public void testArraycopy() throws Exception {
        char[] src = " World".toCharArray();
        char[] dest = {'H', 'e', 'l', 'l', 'o', 0, 0, 0, 0, 0, 0};
        System.arraycopy(src, 0, dest, 5, 6);
        Assertions.assertEquals("Hello World", new String(dest));
    }

    @Test
    public void testGetProperty() {
        String prop = System.getProperty("java.version");
        //System.out.println(prop);
    }

    @Test
    public void testNanoTime() {
        // TODO: read bout it!
        long nanoTime = System.nanoTime();
        //System.out.println(nanoTime);
    }

    @Test
    public void testIdentityHashCode() {
        var code = System.identityHashCode(new Object());
        System.out.println("code = " + code);
    }
}
