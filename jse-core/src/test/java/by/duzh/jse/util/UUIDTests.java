package by.duzh.jse.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

// TODO: have a look at https://www.baeldung.com/java-uuid and write some tests
public class UUIDTests {

    private UUID uuid;

    @Test
    public void testCreate() {
        //uuid = new UUID(1l, 10l);
    }

    @Test
    public void testCreateRandomUUID() {
        uuid = UUID.randomUUID();
        Assert.assertEquals(2, uuid.variant());
        Assert.assertEquals(4, uuid.version());
    }
}
